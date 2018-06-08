set JAVA_PATH="java"
set SERVER_ROOT=".."

set CLIENT_IP=127.0.0.1
set CLIENT_PORT=9100
set ID=root
set PW=root

set SERVER_PATH=%SERVER_ROOT%/lib/client_lite.jar
set SERVER_PATH=%SERVER_PATH%;%SERVER_ROOT%/lib/message.jar

%JAVA_PATH% -Dserver=client_lite -Djava.class.path="%SERVER_PATH%" -Donm.home="%SERVER_ROOT%"  insoft.ClientLiteUser %CLIENT_IP% %CLIENT_PORT% %ID% %PW%
