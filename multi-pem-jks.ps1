$PEMFile = "certs.pem"
$JKSFile = "truststore.jks"
$StorePass = "changeit"

# Create temp folder to hold individual cert files
$TempDir = "certs_temp"
New-Item -ItemType Directory -Path $TempDir -Force | Out-Null

# Read full PEM file
$Content = Get-Content $PEMFile -Raw

# Split certificates
$Certs = ($Content -split "-----END CERTIFICATE-----") | Where-Object { $_ -match "BEGIN CERTIFICATE" }

# Process each certificate
$i = 1
foreach ($cert in $Certs) {
    $cert = $cert + "-----END CERTIFICATE-----`r`n"
    $CertFile = "$TempDir\cert$i.pem"
    $cert | Out-File -Encoding ascii -FilePath $CertFile

    # Import into JKS
    & keytool -importcert -noprompt `
        -alias "cert$i" `
        -file $CertFile `
        -keystore $JKSFile `
        -storepass $StorePass

    $i++
}

# Clean up temp files
Remove-Item $TempDir -Recurse -Force
Write-Host "✅ All certificates imported into $JKSFile"