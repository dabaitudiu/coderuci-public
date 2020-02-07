# CoderUCI
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/logo-1.png"/>
</p>

<p align="center">
  <img src="https://img.shields.io/github/license/dabaitudiu/coderuci-public"/>
  <img src="https://img.shields.io/librariesio/github/dabaitudiu/coderuci-public"/>
</p>


CoderUCI is a an MIT-licensed open source project. It is a question and answer site designed for programmers' discussions. Constructed by Spring Boot + MyBatis + MySQL. https://www.coderuci.com

## 1.Introduction

#### Home Page
When visiting the home page, you can have an overview of proposed questions by the users. Every media thumbnails are consists of the questions information: proposer, question content, views, etc. Every question is classified by a tag that shows in the light-blue badge so that you can have a fast and intuitive understanding of the question. Your may also notice the trend of popular topics on the right side.
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/index.gif"/>
</p>

#### GitHub Oauth
This site currently only supports login as a github user. Simply click the signin button on the top-right corner, it will authorize you and redirect you back to the index page. After signing in, you will be able to publish or reply or comment questions.
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/oauth.gif"/>
</p>

#### View and Comment Questions
After clicking a specific question, you will be forward to the question detail page, where you can view it in the rendered markdown form. You can rank the answers by date/likes. Of course, you can answer the questions. The site also supports secondary comments, where you can have furthur discussions.
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/comment.gif"/>
</p>

#### Publish Questions
Clicking the Publish Button, you will be able to publish your questions. The editor is embedded with a markdown editor, so feel free to add codes!
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/publish.gif"/>
</p>

#### Delete or Edit Your Questions
If you wish to delete/edit your questions, simply click the buttons around the title. But of course, you will only be able to do the operations to your own articles.
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/delete_edit.gif"/>
</p>

#### Search a question
If you don't want to search specific questions, you can click tags on the right of the index page, or search in the nav bar.
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/search.gif"/>
</p>

#### Check Notifications and Published Questions
This site supports notification functions. If someone replied to your questions/comments, you will be notified. Unread notifications will be marked with a yellow badge indicating its status. In addition, you can view all the questions you have published in the 'My Questions' tab.
<p align="center">
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/notification.gif"/>
</p>

## 2.Efficient Features
#### 2-1 MyBatis Generator
<p>
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/mybatis.jpeg" width=50/>
</p>

MyBatis framework provides friendly interaction between user and database operations. A simple usage of mybatis is to add ```@Mapper```annotations to a mapper class that contacts with the database tables, then a person should add SQL sentences to retrieve expected sentences. However, every time if we modifies the database, or modified our methods that handling data, we need to modify the SQL sentences as well. With the assistance of mybatis generator, we can let the mybatis generate mapper according to the database automatically, and provides an encapsulated api for us to handle database operations without using SQL sentences. This is crucial to a project that needs long term maintainence. 

#### 2-2 Flyway 
<p>
  <img src="https://github.com/dabaitudiu/coderuci-public/blob/master/images/flyway.png" width=50/>
</p>

Flyway is an open-source database-migration tool. Before we make modifications to the databases, we can write the commands to a flyway file so that all the scripts will be tracked and preserved like GIT. This will hugely simplify the difficulty of deployment for users other than the author

#### 2-3 Partially refresh page
This is a trivial but important function in the question page. We may want to rank the answers according to the likes count/date published. However, this GET/POST request asking the database to change the orders of results may triggers a refresh of the whole webpage. To prevent that, a partial refresh function is implemented in the QuesitonController, so that only the expected results will be refreshed.

#### 2-4 Customized Exceptions
When users visit our website, exceptions may happens due to: page not exist, access not allowed, or server errors. The website is implemented with customized exception handlers that will indicate specific reasons to the users if a page cannot be visited.

#### 2-5 Session Interceptor
In order to pass the cookie of a signed in user, a session interceptor is embedded in the project so that the status will be kept until the user logs out or cookies expires.

#### 2-6 Spring Boot Task Scheduler 
In order to rank the popular tags, we need to make statistics of questions based on their tags, views, likes and comments. However, if this ranking procedure is processed every time a user visits the site, it is very inefficient, because the server will execute commands in the form : ```sql select tag from question order by popular desc```. If we want to explain the 'popular' index further, we have to spend more time handling this operation. Using a task scheduler will help us a lot. We will schedule a check of current popular index every N minutes, then update the results. This will saves us much time if the influx is high.



