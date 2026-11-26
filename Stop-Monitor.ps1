$ErrorActionPreference='Stop'
Get-ChildItem -LiteralPath "$PSScriptRoot/run" -Filter '*.json' | ForEach-Object {
 $state=Get-Content -LiteralPath $_.FullName -Raw|ConvertFrom-Json
 $proc=Get-Process -Id $state.Pid -ErrorAction SilentlyContinue
 if($proc -and $proc.StartTime.ToUniversalTime().Ticks -eq ([datetime]$state.StartTime).ToUniversalTime().Ticks -and $proc.Path -and [IO.Path]::GetFullPath($proc.Path) -eq [IO.Path]::GetFullPath($state.Exe)){Stop-Process -Id $state.Pid;Write-Output "Stopped $($_.BaseName)"}
}
