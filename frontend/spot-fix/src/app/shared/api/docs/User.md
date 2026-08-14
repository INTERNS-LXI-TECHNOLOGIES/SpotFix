
# User


## Properties

Name | Type
------------ | -------------
`createdBy` | string
`createdDate` | Date
`lastModifiedBy` | string
`lastModifiedDate` | Date
`id` | number
`login` | string
`firstName` | string
`lastName` | string
`email` | string
`activated` | boolean
`langKey` | string
`imageUrl` | string
`resetDate` | Date

## Example

```typescript
import type { User } from ''

// TODO: Update the object below with actual values
const example = {
  "createdBy": null,
  "createdDate": null,
  "lastModifiedBy": null,
  "lastModifiedDate": null,
  "id": null,
  "login": null,
  "firstName": null,
  "lastName": null,
  "email": null,
  "activated": null,
  "langKey": null,
  "imageUrl": null,
  "resetDate": null,
} satisfies User

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as User
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


