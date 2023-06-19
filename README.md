# graphql-java

 1. create a schema
 2. get the schema parser instance
 3. parse the file which has schema
 4. get the type definition of the schema from the schema parser
 5. RuntimeWiring class is then used to wire together the schema with the actual data fetchers and resolvers.
 6. A DataFetcher provides the data for a field. When one is not configured, a PropertyDataFetcher is used.
 7. PropertyDataFetcher fetches data from Map and Java Beans.
 8. So when the field name matches the Map key or the property name of the source Object, no DataFetcher is needed.
 9. Type resolver is used to resolve the type


# My learning

## Basic 
 1. A GraphQL service is created by defining types and fields on those types.
 2. Then providing functions for each field on each type.

e.g. 

`type Query{
    me: User
}`

`type User {
    id: ID
    name: String
}`

Along with functions for each field on each type:

`function Query_me(request) {
    return request.auth.user
}`

`function User_name(user) {
    return user.getName()
}`

For example, the query:

`{
    me {
        name
    }
}`

Could produce the following JSON result:

`{
    "me": {
        "name": "Luke Skywalker"
    }
}`

### The Query and Mutation types

Most types in your schema will just be normal object types, but there are two types that are special within a schema:

`schema {
    query: Query
    mutation: Mutation
}`

Every GraphQL service has a query type and may or may not have a mutation type. These types are the same as a regular object type, but they are special because they define the entry point of every GraphQL query.

### Note : Data fetchers are sometimes called "resolvers" in other graphql implementations.

### Note : A TypeResolver helps graphql-java to decide which type a concrete value belongs to. This is needed for Interface and Union.