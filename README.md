### Setup for local development

#### Start environment with DB
```
docker-compose up -d
```

#### Connect to DB
```
psql -h localhost -p 5432 -U collectables -d collectables 
```
enter password `s3cr3t`

#### Stop environment with DB
```
docker-compose down
```
  
