# Feature Toggle Service

Java based REST service enabling Feature Toggle use from an external service.  The initial build is focused on being
able to deploy to Heroku.

## Build - builds war etc in target directory

```$ mvn clean package```

#### NOTE: The files of src/main/webapp/WEB-INF and src/main/resources are filtered from src/config/ to enable configuration from local settings files.  Any files added directly into main/webapp/WEB-INF or src/resources are removed during a clean and ignored in git.

## Run Locally - starts an embedded Jetty server at localhost:8080

```$ mvn clean package jetty:run```

#### Add a settings.xml file to the ~/.m2 directory to configure datasource.  Sample file:

<pre>
<code>
&lt;settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd"&gt;
    &lt;profiles&gt;
        &lt;profile&gt;
            &lt;id&gt;env-dev&lt;/id&gt;
            &lt;activation&gt;
                &lt;property&gt;
                    &lt;name&gt;env&lt;/name&gt;
                    &lt;value&gt;dev&lt;/value&gt;
                &lt;/property&gt;
                &lt;activeByDefault&gt;true&lt;/activeByDefault&gt;
            &lt;/activation&gt;
            &lt;properties&gt;
                &lt;jdbc.url&gt;jdbc:postgresql://localhost:3306/ft_dev&lt;/jdbc.url&gt;
                &lt;jdbc.user&gt;ft_dev&lt;/jdbc.user&gt;
                &lt;jdbc.password&gt;ft_dev_password&lt;/jdbc.password&gt;
                &lt;jdbc.host&gt;localhost&lt;/jdbc.host&gt;
                &lt;jdbc.port&gt;5432&lt;/jdbc.port&gt;
                &lt;jdbc.schema&gt;ft_dev&lt;/jdbc.schema&gt;
            &lt;/properties&gt;
        &lt;/profile&gt;
    &lt;/profiles&gt;
&lt;/settings&gt;
</code>
</pre>

#### In order to debug it is necessary to export maven options

```$ export MAVEN_OPTS="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=5005,server=y,suspend=n"```

## Run Tests

```$ mvn test```
