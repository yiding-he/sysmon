mkdir 91-sysmon-agent
cd 91-sysmon-agent/
wget -q http://10.10.22.212:32123/01-start.sh -O 01-start.sh
wget -q http://10.10.22.212:32123/jar-runner.jar -O jar-runner.jar
sh 01-start.sh
