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