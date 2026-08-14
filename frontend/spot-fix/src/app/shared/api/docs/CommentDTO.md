
# CommentDTO


## Properties

Name | Type
------------ | -------------
`id` | number
`title` | string
`content` | string
`createdDate` | Date
`updatedDate` | Date
`deleted` | boolean
`deletedDate` | Date
`ticket` | [TicketDTO](TicketDTO.md)
`user` | [UserDTO](UserDTO.md)

## Example

```typescript
import type { CommentDTO } from ''

// TODO: Update the object below with actual values
const example = {
  "id": null,
  "title": null,
  "content": null,
  "createdDate": null,
  "updatedDate": null,
  "deleted": null,
  "deletedDate": null,
  "ticket": null,
  "user": null,
} satisfies CommentDTO

console.log(example)

// Convert the instance to a JSON string
const exampleJSON: string = JSON.stringify(example)
console.log(exampleJSON)

// Parse the JSON string back to an object
const exampleParsed = JSON.parse(exampleJSON) as CommentDTO
console.log(exampleParsed)
```

[[Back to top]](#) [[Back to API list]](../README.md#api-endpoints) [[Back to Model list]](../README.md#models) [[Back to README]](../README.md)


