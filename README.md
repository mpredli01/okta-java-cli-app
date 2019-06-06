## Demonstration of Okta Java SDK Application

### Michael P. Redlich
### June 5, 2019

This is a simple CLI application to demonstrate the use of Okta's Java SDK.
It was built using [Maven](http://maven.apache.org/) and the [Amazon Corretto 11](https://docs.aws.amazon.com/corretto/latest/corretto-11-ug/downloads-list.html) downstream distribution of [OpenJDK](https://openjdk.java.net/).

To build the application:

```
mvn clean compile
```

To execute the application:

```
mvn exec:java \
    -Dexec.mainClass=org.redlich.okta.DemoApplication \
    -Dexec.args="https://dev-287826.okta.com/oauth2/default false false"
```

Alternatively, you can use the `run_app.sh` script as follows:

```
./run_app.sh \
    --is https://dev-287826.okta.com/oauth2/default \
    --nu false \
    --cp false
```

This application requires three (3) command-line parameters:

* the issuer URL 
* true | false for creating a new user
* true | false for changing the user password  

#### Note
This application will **temporarily** have authentication hard-coded into it, but this will ultimately change to include generic placeholders.
I will update this `README.md` file to include instructions on how to create a forever-free Okta [developer account](https://developer.okta.com/).
