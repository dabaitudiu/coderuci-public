# coderuci
Quora-like spring boot project

```
33 : Finish Problem Updates
    
     Flow: 1. imagine, if we click "edit' icon, the web page should navigate to /publish/id
           2. Thus, go to QuestionController, we find there is no routing method for "/publish/{id}, thus we create one.
           3. Because the question has already existed, we can retrieve its information from the database,
              using the sentence "select * from question where id = #{id}"
           4. However, because there needs to be a medium between controller level and mapper level, thus we use the QuestionDTO
              to finish this process.
           5. Looking downwards in the same file, we copy the "model.addAttributes" of questions features,
              which are passed by posting in the original publish method.
           6. Later, we can find a same problem as User login before, which is : every time we edit the question,
              we need to update it, rather than insert a new one. Thus, in the original 'postPublish' method, we should
              change the 'createQuestion' method to 'createOrUpdate' method.
           7. However, to update, we need to identify the question using its id, whereas there is no id
              passed through the postPublish method. (only title, description, tag). Thus, we need to add an extra parameter
              in the same form as the other three. Meanwhile, in the HTML file, we to add a hidden input contains value id, 
              which will be passed in with values if we are doing an update, and null (or whatever) if we just do an insertion.
              In the createOrUpdate method, we will create a new question and generate an id if we are doing the insertion process.
           8. Debug, all done.



     Question: Every time we change something in the database,
               we can use fly migrate to store the scripts and become convenient.
               However, if we change some thing (e.g. add attributes to some tables),
               we must change MyBatis translation (or, the sql sentences in the mapper)
               Are there any easier way to do this?


34:
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate


35. 
White Page: Override default 'BasicController'
Controller Advice & Exception Handler to process all events exceptions. 
Excpetion should be defined as Runtime Exception, so that it won't affect our other codes.

CustomizeExceptionHandler is a class that intercept exceptions add pass info to a model
The above handles the situation that exception happens passively.

We also need an error controller that directly points to the error routing.

36. 
Viewcount: function executed in the question controller(means every time visited, then increase)
However, we may face the situation that many people visiting a question at the same time,
and will cause wrong calculation to the counting. Thus we rewrite a mapper function as viewcount = viewcount + 1 
directly in a database operation, rather than get the view data, plus one, and save back.

37. Create a Comment Controller.
Receive a comment JSON from front end, post to the method in comment controller

41. Sign in successfully and redirect to the page you leave.

42. I SUCCEED!!!!! Partially Refresh the comment page!

44. Manage the toggle button. Bootstrap 4 sample : change the given button to our reply button, bond with id (th:id, th:data-target)
    Meanwhile add a shine css using jquery, when click change btn-light to btn-info

45. Secondary comment implemented.
```