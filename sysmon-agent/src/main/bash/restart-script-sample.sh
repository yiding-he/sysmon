wget -q http://10.10.22.212:32123/01-start.sh -O 01-start.sh
wget -q http://10.10.22.212:32123/02-restart.sh -O 02-restart.sh

OLD_PID=$(ps aux | grep '[c]om.hyd.sysmon.agent.AgentMain' | awk '{print $2}')
echo "old pid: ${OLD_PID}" >> restart.log

SELF_PATH=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/$0
export HOST_NAME=$(cat ~/.host_name)

jars=http://10.10.22.212:32123/sysmon-agent-1.0-SNAPSHOT.jar
updateUrl=http://10.10.22.212:32123/update
mainClass=com.hyd.sysmon.agent.AgentMain
jarRunner=com.hyd.remotejarrunner.RemoteJarRunner

nohup java \
  -Djars=${jars} -DmainClass=${mainClass} \
  -Drestart=${SELF_PATH} \
  -cp jar-runner.jar ${jarRunner} ${updateUrl} > agent.log &

echo "new java process started, killing ${OLD_PID}..." >> restart.log

kill $OLD_PID;
while kill -0 $OLD_PID; do sleep 1; done;
