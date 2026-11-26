param(
    [string] $MysqlExe = 'mysql.exe',
    [int] $Port = 13306,
    [string] $DatabaseUser = 'jpm_local',
    [securestring] $DatabasePassword,
    [securestring] $ApplicationPassword
)

$ErrorActionPreference = 'Stop'
$mysql = (Get-Command $MysqlExe).Source
$node = (Get-Command node.exe).Source
if (!$DatabasePassword) {
    $DatabasePassword = Read-Host "Local MySQL password for $DatabaseUser" -AsSecureString
}
if (!$ApplicationPassword) {
    $ApplicationPassword = Read-Host 'New website password for studio_monitor (at least 10 characters)' -AsSecureString
}
if ($ApplicationPassword.Length -lt 10) {
    throw 'Use at least 10 characters for the website password.'
}

$salt = [Guid]::NewGuid().ToString('N').ToUpperInvariant()
$originalMysqlPassword = $env:MYSQL_PWD
$originalStudioPassword = $env:STUDIO_MONITOR_NEW_PASSWORD
try {
    $env:STUDIO_MONITOR_NEW_PASSWORD = [Net.NetworkCredential]::new('', $ApplicationPassword).Password
    $hash = & $node -e "const crypto = require('node:crypto'); const hash = value => crypto.createHash('sm3').update(value, 'utf8').digest('base64'); const salt = process.argv[1]; process.stdout.write(hash(hash(process.env.STUDIO_MONITOR_NEW_PASSWORD) + salt + salt));" $salt
    if ($LASTEXITCODE -ne 0 -or $hash -notmatch '^[A-Za-z0-9+/]{43}=$') {
        throw 'Could not generate the expected SM3 password hash.'
    }
    $env:STUDIO_MONITOR_NEW_PASSWORD = $originalStudioPassword
    $env:MYSQL_PWD = [Net.NetworkCredential]::new('', $DatabasePassword).Password
    $statement = @"
UPDATE sys_user
SET U_PassWord = '$hash', U_Salt = '$salt', U_State = '1', U_Pass_Update_Time = NOW()
WHERE U_ID = 'studio-monitor-user' AND U_Account = 'studio_monitor';
SELECT ROW_COUNT();
"@
    $result = $statement | & $mysql --no-defaults --protocol=TCP --host=127.0.0.1 "--port=$Port" "--user=$DatabaseUser" --default-character-set=utf8mb4 --batch --skip-column-names vue_antd_admin
    if ($LASTEXITCODE -ne 0 -or $result -ne '1') {
        throw 'Account activation failed. Check the local MySQL connection and import database/auth-init.sql first.'
    }
    Write-Output 'Website account studio_monitor is ready.'
}
finally {
    $env:STUDIO_MONITOR_NEW_PASSWORD = $originalStudioPassword
    $env:MYSQL_PWD = $originalMysqlPassword
}
