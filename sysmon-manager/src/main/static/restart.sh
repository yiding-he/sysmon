#!/usr/bin/env bash

SYSMON_SERVER=http://SYSMON_SERVER:32123
MAIN_CLASS=com.hyd.sysmon.agent.AgentMain

######## Functions
kill_old_process() {
  OLD_PID=$1
  if [ -n "${OLD_PID}" ]; then
    echo "Killing old process" ${OLD_PID} "..."
    {
      kill ${OLD_PID};
      while kill -0 ${OLD_PID}; do sleep 1; done;
    } 2> /dev/null
    echo "Old process" ${OLD_PID} "killed."
  else
    echo "Old process not found."
  fi
}

######## Self update
wget -q ${SYSMON_SERVER}/01-start.sh -O 01-start.sh
wget -q ${SYSMON_SERVER}/jar-runner.jar -O jar-runner.jar

######## Get current running java process
OLD_PID=$(ps aux | grep "[${MAIN_CLASS:0:1}]${MAIN_CLASS:1}" | awk '{print $2}')

######## Prepare arguments and env variables
SELF_PATH=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )/$0
export HOST_NAME=$(cat ~/.host_name)

JARS=${SYSMON_SERVER}/sysmon-agent-1.0-SNAPSHOT.jar
SYSMON_UPDATE_URL=${SYSMON_SERVER}/update

######## Kill old java process
kill_old_process ${OLD_PID}

######## Start new java process
echo "Starting new java process..."
{
  nohup java \
    -Djars=${JARS} \
    -DmainClass=${MAIN_CLASS} \
    -Drestart=${SELF_PATH} \
    -jar jar-runner.jar ${SYSMON_UPDATE_URL} > agent.log &
} 2> /dev/null
