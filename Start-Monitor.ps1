$ErrorActionPreference = 'Stop'
$root = $PSScriptRoot
$java = (Get-Command java.exe).Source
$node = (Get-Command node.exe).Source
$jarDirectory = Join-Path $root 'private/backend-jars'
$configDirectory = (Join-Path $root 'backend-config').Replace('\', '/')
$services = [ordered]@{
    eureka = 8081
    user = 8082
    'api-utils' = 8192
    projectProducer = 8091
    projectConsumer = 8092
    file = 8087
    'jp-bpm' = 8093
    'jpm-custom' = 8116
    zuul = 8085
}
function Confirm-Port([int] $Port) {
    $tcp = [Net.Sockets.TcpClient]::new()
    try { $tcp.Connect('127.0.0.1', $Port) }
    finally { $tcp.Dispose() }
}
foreach ($service in $services.Keys) {
    if (!(Test-Path -LiteralPath (Join-Path $jarDirectory "$service.jar") -PathType Leaf)) {
        throw "Missing private/backend-jars/$service.jar"
    }
}
foreach ($port in 13306, 16379) {
    try { Confirm-Port $port }
    catch { throw "Start the local dependency listening on port $port first." }
}
foreach ($port in (@($services.Values) + 18080)) {
    $test = [Net.Sockets.TcpListener]::new([Net.IPAddress]::Loopback, $port)
    try { $test.Start() }
    catch { throw "Port $port is already in use." }
    finally { $test.Stop() }
}
foreach ($name in 'logs', 'run') {
    New-Item -ItemType Directory -Path (Join-Path $root $name) -Force | Out-Null
}

function Start-MonitorProcess([string] $Name, [string] $Exe, [string[]] $Arguments) {
    $process = Start-Process -FilePath $Exe -ArgumentList $Arguments -WorkingDirectory $root -WindowStyle Hidden -PassThru `
        -RedirectStandardOutput (Join-Path $root "logs/$Name.log") -RedirectStandardError (Join-Path $root "logs/$Name.err.log")
    @{ Pid = $process.Id; StartTime = $process.StartTime.ToString('o'); Exe = $Exe } |
        ConvertTo-Json | Set-Content -LiteralPath (Join-Path $root "run/$Name.json") -Encoding UTF8
    Write-Output "Started $Name"
}

$initialPassword = $env:JPM_LOCAL_DB_PASSWORD
if (!$initialPassword) {
    $password = Read-Host 'MySQL password for jpm_local' -AsSecureString
    $env:JPM_LOCAL_DB_PASSWORD = [Net.NetworkCredential]::new('', $password).Password
}
try {
    foreach ($name in $services.Keys) {
        $jar = Join-Path $jarDirectory "$name.jar"
        $port = $services[$name]
        $launchArguments = @(
            '-Xms64m', '-Xmx512m', '-Dfile.encoding=UTF-8', '-jar', ('"' + $jar + '"'),
            '--spring.profiles.active=mysql,local',
            ('"--spring.config.additional-location=file:' + $configDirectory + '/application-local.properties,file:' + $configDirectory + '/database-local.properties"'),
            "--server.port=$port", "--management.server.port=$port",
            '--ezviz.preview.allow-app-token-link=true', '--logging.level.com.jpmsoft.projectProducer.mapper=WARN'
        )
        if ($name -eq 'eureka') {
            $launchArguments = @('-Xmx256m', '-Dfile.encoding=UTF-8', '-jar', ('"' + $jar + '"'),
                ('"--spring.config.location=file:' + $configDirectory + '/eureka-local.yml"'))
        }
        Start-MonitorProcess $name $java $launchArguments
    }
}
finally {
    if ($null -eq $initialPassword) { Remove-Item Env:JPM_LOCAL_DB_PASSWORD -ErrorAction SilentlyContinue }
}
$nodeEntry = Join-Path $root 'frontend-runtime/monitor-server.cjs'
Start-MonitorProcess 'frontend' $node @('"' + $nodeEntry + '"')
Write-Output 'Open http://127.0.0.1:18080/hbgpc/video/ after the services finish starting.'
