## Demonstration of Okta Java SDK Application

### Michael P. Redlich
### June 5, 2019

This is a simple CLI application to demonstrate the use of Okta's Java SDK.
It was built using [Maven](http://maven.apache.org/) and the [Amazon Corretto 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) downstream distribution of [OpenJDK](https://openjdk.java.net/).

Build the app:

`mvn clean compile`

Execute the app:

`mvn exec:java -Dexec.mainClass=org.redlich.okta.DemoApplication -Dexec.args="https://dev-287826.okta.com/oauth2/default false"`

The app requires two command line parameter:

[a] the issuer URL 

[b] true | false to direct  



