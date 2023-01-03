## About this project
First of all, this is a **software testing** project.

This project tries to simulate the basic actions of a user on **Twitter**, for example: following a user, liking a tweet, or posting a tweet. 
I'm using  [Twitter API v2](https://developer.twitter.com/en/docs/twitter-api) to achieve this goal.

The tests focus on testing the API performance, so I'm using a performance testing tool called **Gatling**. In the next section, we will talk more about it.


## About Gatling
What is Gatling?
Gatling is a powerful open-source load testing solution.

Gatling is designed for continuous load testing and integrates with your development pipeline. Gatling includes a web recorder and colorful reports.

[Official Documentation](https://gatling.io/docs/gatling/)
## Requirements

- JDK 8 or above
- Gradle

## Test scenario
### Basic Scenario Specification
1. Open the app/website
   1. Get information about me
2. Network
   1. Search a **user**
   2. Look at the **user** timeline
   3. Follow the **user**
3. Timeline
   1. Look at the timeline
   2. Look a **tweet**
   3. Like the **tweet**
   4. Re**tweet**
   5. Bookmark the **tweet**
4. Tweeting
   1. Create a simple tweet
   2. Create a tweet with 3 threats
   3. Create a poll tweet

## Running test using Gradle
```
gradle gatlingRun-cl.gera.apitwitter2.simulations.BasicSimulation
```
