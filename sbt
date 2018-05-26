java -Xms512M -Xmx2048M -Xss1M -XX:+CMSClassUnloadingEnabled  -Dfile.encoding=UTF-8 -jar `dirname $0`/sbt-launch.jar "$@"
