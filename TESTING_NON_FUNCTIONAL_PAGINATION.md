

#Teaching-HEIGVD-AMT-2018-Project

##Testing non functional pagination

### Introduction

This document describe our experiment to point out the difference of using pagination in the business or resources tier.

### Testing

In our master branch we are using pagination in the resources tier so we have to make a new branch call test_non_func_pagination to implement the pagination in the business tier. To have interesting tests we must have enough "user applications", so we have a test class that insert 1'000 applications in the database (in the test_non_func_pagination branch). 

For the testes we are using JMeter, in the test plan we have a unit group with 10 users, this group manage cookies, has a simple controller which send an HTTP request tot get logged in and a loop controller that request 100 times the applications page (the page where a user see his applications). In summary we make 10 users requesting 100 times a page with pagination. 

The JMeter configuration can be found in the file pagination.jmx

### Results

- In Business: there is no problem and all request are responded to in 11 secondes
- In Resources: the process take 1 minute and 26 seconds, the tow last requests are responded with a 500 error and the server show us an error message in the logs: java.lang.OutOfMemoryError: GC overhead limit exceeded

### Conclusion

This test showed us how important it is to use the right tools, someone can easily make this mistake just be cause he knows Java better than SQL and don't want to "waste" time to find the right SQL query. 