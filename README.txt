This will eventually explain the setup required for this to work.

Elastic Beanstalk
IAM identity with R/O permission to S3
Set Key & Secret from IAM identity in Beanstalk environment config
For local development, set and secret using AWS CLI configuration 

* S3 configuration

* How to upload album content.

Google Analytics
* Set tracking ID in private.properties (see private.properties.template)


// Some notes for me so I spend less time trying to remember the basics every time I need to make a code change:

To run in Eclipse:
* Run --> Tomcat v8.0 Server at localhost

To build from command line:
* mvn clean; mvn package

To diff vs repository:
* git status; git diff

To sync code:
* git pull

To commit code changes:
* git commit -m "message" ; git push

To deploy:
* Upload package to Elastic Beanstalk