# Simple Pagination Library
A simple pagination library for Java, which lets you implement pagination into your web application more elegantly.

## What is pagination?
Pagination is the process of separating a sequential list into discrete pages.
It also refers to a sequential numbering to those separete pages.

This library calculates all possible indices/URIs that navigate to the separate pages.
Developers can specify: how many items to show per page, the number of visible indices, and the current page index.

## How to use
Really simple to use. Only two or three lines will do:
```java
// Create an instance first
Pagination p = new Pagination("http://localhost/", "pageNum");
// Get a list of all the possible pagination URLs
List<String> urls = p.makeURLs(itemsSize, itemsLength, indicesLength, index);
// Get a list of all the possible pagination indices
List<Integer> nums = p.makeIndices(itemsSize, itemsLength, indicesLength, index);
```
#### Parameters
| parameter       | type    | description                            |
| --------------- | ------- | -------------------------------------- |
| `itemsSize`     | int     | the number of all items in total       |
| `itemsLength`   | int     | the number of all items in one page    |
| `indicesLength` | int     | the number of indices to be displayed  |
| `index`         | int     | the current page index, starts at one  |

If you wish to use other GET parameters, this constructor should play a role:
```java
// params indicates all the HTTP GET parameters except the indexing parameter
// paramPage is a parameter key for specifying a current index
Pagination(String baseURL, Map<String, String> params, String paramPage)
```

You can also fetch the URL address/index for next page:
```java
// URL to navigate users to next page
String nextURL = p.getNext();
// Next page index, returns -1 if there's no more page
int nextIndex = p.getNextNum();
```

