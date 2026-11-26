param(
    [ValidateSet('test', 'package')][string]$Goal = 'test',
    [string]$JdkHome
)
$ErrorActionPreference = 'Stop'
if (-not $JdkHome) {
    $javaCommand = Get-Command java.exe -ErrorAction SilentlyContinue
    if ($javaCommand) { $JdkHome = Split-Path (Split-Path $javaCommand.Source -Parent) -Parent }
}
if (-not $JdkHome -or -not (Test-Path -LiteralPath (Join-Path $JdkHome 'bin\javac.exe'))) {
    throw 'JDK not found. Pass -JdkHome with your Java 8 JDK directory.'
}
$releaseFile = Join-Path $JdkHome 'release'
if (-not (Test-Path -LiteralPath $releaseFile) -or
    (Get-Content -LiteralPath $releaseFile -Raw) -notmatch '(?m)^JAVA_VERSION="1\.8\.') {
    throw 'This project uses Java 8. Pass -JdkHome pointing to a Java 8 JDK.'
}
$mavenCommand = Get-Command mvn.cmd -ErrorAction SilentlyContinue
if ($mavenCommand) {
    $videoMaven = $mavenCommand.Source
} else {
    $wrapperCache = Join-Path $env:USERPROFILE '.m2\wrapper\dists'
    $videoMaven = Get-ChildItem -LiteralPath $wrapperCache -Filter mvn.cmd -Recurse -File -ErrorAction SilentlyContinue |
        Sort-Object FullName -Descending | Select-Object -First 1 -ExpandProperty FullName
}
if (-not $videoMaven) { throw 'Maven not found. Configure Maven in IDEA or add mvn.cmd to PATH.' }
$previousJavaHome = $env:JAVA_HOME
try {
    # Process-local setting only: Maven must use the same JDK as the selected java command.
    $env:JAVA_HOME = $JdkHome
    Write-Output "Build JDK: $JdkHome"
    & $videoMaven -B -ntp -f (Join-Path $PSScriptRoot 'pom.xml') $Goal
    if ($LASTEXITCODE -ne 0) { throw "Maven failed: $LASTEXITCODE" }
} finally {
    $env:JAVA_HOME = $previousJavaHome
}
