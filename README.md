# aws-image-upload
I am following along with a Java Spring Boot
[tutorial on YouTube](https://www.youtube.com/watch?v=i-hoSg8iRG0&t=1545s).

I decided to do several things differently such as not directly putting the access key and secret into the code.
I opted for reading in a csv file that AWS allows you to download when creating the access keys. 

I also followed the recommendation of not creating access keys directly to the root user, and instead created a new user and provided 
that user the access keys while limiting access to S3.
