# keycloak-sha1-password-hash-provider
Sample password hashing provider for keycloak.

Based on https://github.com/nicolabeghin/keycloak-sha1-salted

## Setup keycloak server locally

### Prerequisites
1. java 11
2. keycloak server(see the [installation](#Installing keycloak server) steps below)

### Build the application
```shell
mvn clean install
```

### Installing the password-hash-provider in keycloak server
```shell
cp ./target/keycloak-sha1-password-hash-provider-*.jar ${KEYCLOAK_HOME}/providers/ 
```

### Start keycloak server
```shell
cd ${KEYCLOAK_HOME}/bin
./kc.sh start-dev --http-port=8083 --log-level=DEBUG
```

### Using password-hash-provider in keycloak(one time setup)
To use the provider, add the parameter --spi-password-hashing-provider=sha1-salted in the build parameters
OR 
Add 'sha1-salted' as the password hashing algorithm in the authentication policy settings for the realm
(Authentication>>Polices Tab>>Password policy Tab>>select 'Hashing Algorithm' from drop down>>Add 'sha1-salted' as your Hashing Algorithm)

### Sample user insertion
```shell
curl --location --request POST 'http://localhost:8083/admin/realms/demo1/users' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <your-access-token>' \
--data-raw '{
        "username": "user1",
        "enabled": true,
        "totp": false,
        "emailVerified": true,
        "firstName": "user1",
        "lastName": "user1",
        "email": "user1@test.com",
        "credentials": [
        {
          "algorithm": "sha1-salted",
          "hashedSaltedValue": "9282d06b77e03989da6c0d86479ba73ac8691cfc",
          "salt": "cXdlcnR5",
          "hashIterations": 1,
          "type": "password"
        }
      ],
      "disableableCredentialTypes": [],
      "requiredActions": [],
      "realmRoles": [
        "offline_access",
        "uma_authorization"
      ],
      "clientRoles": {
        "account": [
          "manage-account",
          "view-profile"
        ]
      }
    }'
```

After creating the above user, try to login with username:user1 and password:123456789

## Installing keycloak server
1. Download latest http://www.keycloak.org/downloads.html
2. unzip keycloak-20.0.1.zip
3. cd keycloak-20.0.1/bin
4. start the keycloak server
   ```
    ./kc.sh start-dev
    or
    ./kc.sh start-dev --http-port=8083
    or
    ./kc.sh start-dev --http-port=8083 --log-level=DEBUG
    ```

* for downloading old versions: https://github.com/keycloak/keycloak/releases/download/19.0.1/keycloak-19.0.1.zip




