### Setup for local development

#### Start environment with DB
```
docker-compose up -d
```

#### Connect to DB
```
psql -h localhost -p 5432 -U collectables -d collectables 
```
enter password `geheim1`

#### Stop environment with DB
```
docker-compose down
```
   
### Testing
Docker needs to run for the test database    
  


