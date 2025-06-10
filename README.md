# data-pipeline-testing2


I want to build a testing project that uses cucumberWithSerenity selenium, can you please use the serenity design pattern which is gherkin -> stepdefinitions -> stepclass -> Pages

My initial use case and problemstameent are listed below

Open the prophecy page and login, prophecy does not uses username/pass authentication in my company, instead it is using an SSO based login using ADFS.
Open the particular pipeline and execute the pipeline stage by stage and validate records at every stage
When starting the pipeline, see if we can conigure a custome data source, json or any tabular data source to that pipeline and it should run the pipeline with the mock data and assertions should be done at each pipeline stages


Can you please discard all these changes, and i want you to redesign this framework to only support Prophecy pipeline testing, basically, i should start a pipeline and test every stage by stage execution, and also is there a way to pass the output source a mock location data source, so that i can cleanup the entire data which is created as part of the pipeline execution, or is there a way to give mock data to an existign pipeline and run my pipeline based on that mock data, so that i dont have to test on the real dabtabricks data source

Can you please remove all other code base which is not required, my only requirement is to

Open the prophecy page and login
Open pipeline and execute the pipeline stage by stage and validate records
i should be able to pass the custom json or any tabular data source to that pipeline and it should run the pipeline with the mock data and assertions should be done at each pipeline stages
requesting you to remove the unnecessary codebase, and also selenium provides PageObject can you please extend that and reuse the functionality instead of creating your own custom drivermanager

Can you also provide me gherkin scenarios, all possible scenarios

Actually there is one problem, in my login page, it does not used email and password it is using some signin with okta, and internall in my company auth is taking care by ADFS, how should i login now?

Actually it is an SSO login, so i wont have username and passsword



i am using cucumberWithSerenity, can you please update the design pattern, I think, gherkin -> stepdefinitions -. stepclass -> Pages
