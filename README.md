# Grafungi

The simple graph library. It contains an embedded graph database, webservice for multi entity graphs and bayesian networks.

## Starting

```
git clone https://github.com/flohannes/grafungi.git
cd grafungi
mvn clean install
java -jar query-ws/target/query-ws-1.0-SNAPSHOT.jar -port 8012 -dbpath /anypathto/graphdata/ -dummydata
```

## Test
Test by enter the following link to the browser: http://localhost:8012/graphs/all

## BN Visualization
open the following file with a browser: bnvis/Vis.html
