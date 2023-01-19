@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  app startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and APP_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\app-1.0-SNAPSHOT.jar;%APP_HOME%\lib\javalin-4.0.1.jar;%APP_HOME%\lib\slf4j-simple-1.7.31.jar;%APP_HOME%\lib\thymeleaf-layout-dialect-3.0.0.jar;%APP_HOME%\lib\thymeleaf-extras-java8time-3.0.4.RELEASE.jar;%APP_HOME%\lib\thymeleaf-expression-processor-3.0.0.jar;%APP_HOME%\lib\thymeleaf-3.0.12.RELEASE.jar;%APP_HOME%\lib\bootstrap-5.1.1.jar;%APP_HOME%\lib\h2-1.4.200.jar;%APP_HOME%\lib\postgresql-42.2.24.jar;%APP_HOME%\lib\ebean-12.11.5.jar;%APP_HOME%\lib\ebean-ddl-generator-12.11.5.jar;%APP_HOME%\lib\ebean-querybean-12.11.5.jar;%APP_HOME%\lib\ebean-migration-12.11.2.jar;%APP_HOME%\lib\ebean-core-12.11.5.jar;%APP_HOME%\lib\ebean-core-type-12.11.5.jar;%APP_HOME%\lib\ebean-api-12.11.5.jar;%APP_HOME%\lib\ebean-annotation-7.3.jar;%APP_HOME%\lib\jaxb-runtime-2.3.5.jar;%APP_HOME%\lib\activation-1.1.1.jar;%APP_HOME%\lib\avaje-config-1.4.jar;%APP_HOME%\lib\slf4j-api-1.7.31.jar;%APP_HOME%\lib\jetty-webapp-9.4.43.v20210629.jar;%APP_HOME%\lib\websocket-server-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-servlet-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-security-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-server-9.4.43.v20210629.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.5.31.jar;%APP_HOME%\lib\ognl-3.1.26.jar;%APP_HOME%\lib\attoparser-2.0.5.RELEASE.jar;%APP_HOME%\lib\unbescape-1.1.6.RELEASE.jar;%APP_HOME%\lib\groovy-extensions-1.1.0.jar;%APP_HOME%\lib\groovy-3.0.8.jar;%APP_HOME%\lib\popper.js-2.9.3.jar;%APP_HOME%\lib\checker-qual-3.5.0.jar;%APP_HOME%\lib\ebean-datasource-7.2.jar;%APP_HOME%\lib\ebean-ddl-runner-1.2.jar;%APP_HOME%\lib\classpath-scanner-6.1.jar;%APP_HOME%\lib\jakarta.xml.bind-api-2.3.3.jar;%APP_HOME%\lib\txw2-2.3.5.jar;%APP_HOME%\lib\istack-commons-runtime-3.0.12.jar;%APP_HOME%\lib\jakarta.activation-1.2.2.jar;%APP_HOME%\lib\websocket-servlet-9.4.43.v20210629.jar;%APP_HOME%\lib\javax.servlet-api-3.1.0.jar;%APP_HOME%\lib\websocket-client-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-client-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-http-9.4.43.v20210629.jar;%APP_HOME%\lib\websocket-common-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-io-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-xml-9.4.43.v20210629.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.5.31.jar;%APP_HOME%\lib\kotlin-stdlib-1.5.31.jar;%APP_HOME%\lib\javassist-3.20.0-GA.jar;%APP_HOME%\lib\ebean-datasource-api-7.2.jar;%APP_HOME%\lib\persistence-api-2.2.5.jar;%APP_HOME%\lib\ebean-types-2.2.jar;%APP_HOME%\lib\ebean-migration-auto-1.1.jar;%APP_HOME%\lib\ebean-externalmapping-api-12.11.5.jar;%APP_HOME%\lib\antlr4-runtime-4.8-1.jar;%APP_HOME%\lib\classpath-scanner-api-6.1.jar;%APP_HOME%\lib\jetty-util-ajax-9.4.43.v20210629.jar;%APP_HOME%\lib\jetty-util-9.4.43.v20210629.jar;%APP_HOME%\lib\websocket-api-9.4.43.v20210629.jar;%APP_HOME%\lib\annotations-13.0.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.5.31.jar


@rem Execute app
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %APP_OPTS%  -classpath "%CLASSPATH%" hexlet.code.App %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable APP_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%APP_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
