
#!/usr/bin/env bash
echo 'tar started '

COPYFILE_DISABLE=1 tar -zcvf jke-1-common-v1.tgz --exclude='.DS_Store'  chart-jke-1-common
COPYFILE_DISABLE=1 tar -zcvf jke-2-web-v1.tgz --exclude='.DS_Store' chart-jke-2-web 
COPYFILE_DISABLE=1 tar -zcvf jke-3-mq-v1.tgz  --exclude='.DS_Store' chart-jke-3-mq
COPYFILE_DISABLE=1 tar -zcvf jke-4-api-v1.tgz  --exclude='.DS_Store' chart-jke-4-api
COPYFILE_DISABLE=1 tar -zcvf jke-5-bankstreet-v1.tgz  --exclude='.DS_Store' chart-jke-5-bankstreet

echo 'tar completed'