# chat-pane



<!-- Auto Generated Below -->


## Properties

| Property                     | Attribute  | Description | Type                              | Default                  |
| ---------------------------- | ---------- | ----------- | --------------------------------- | ------------------------ |
| `mapInputTextToHtmlElements` | --         |             | `(text: string) => HTMLElement[]` | `createElementsFromText` |
| `triangle`                   | `triangle` |             | `"bottom" \| "none" \| "top"`     | `'bottom'`               |


## Events

| Event      | Description | Type                               |
| ---------- | ----------- | ---------------------------------- |
| `incoming` |             | `CustomEvent<IncomingEventDetail>` |


## Methods

### `addButton({ text, action }: { text: string; action: () => any; }) => Promise<HTMLElement>`



#### Parameters

| Name  | Type                                   | Description |
| ----- | -------------------------------------- | ----------- |
| `__0` | `{ text: string; action: () => any; }` |             |

#### Returns

Type: `Promise<HTMLElement>`



### `addCard({ text, image }: { text?: string; image?: string; }) => Promise<HTMLElement>`



#### Parameters

| Name  | Type                                 | Description |
| ----- | ------------------------------------ | ----------- |
| `__0` | `{ text?: string; image?: string; }` |             |

#### Returns

Type: `Promise<HTMLElement>`



### `addIncomingMessage(text: string) => Promise<HTMLChatMessageElement>`



#### Parameters

| Name   | Type     | Description |
| ------ | -------- | ----------- |
| `text` | `string` |             |

#### Returns

Type: `Promise<HTMLChatMessageElement>`



### `addOutgoingMessage(text: string) => Promise<HTMLChatMessageElement>`



#### Parameters

| Name   | Type     | Description |
| ------ | -------- | ----------- |
| `text` | `string` |             |

#### Returns

Type: `Promise<HTMLChatMessageElement>`



### `scrollToBottom() => Promise<void>`



#### Returns

Type: `Promise<void>`




## Dependencies

### Depends on

- [chat-message](../message)
- ion-card
- ion-card-content
- ion-button
- ion-header
- [chat-conversation](../conversation)
- ion-footer
- [chat-input](../input)

### Graph
```mermaid
graph TD;
  chat-pane --> chat-message
  chat-pane --> ion-card
  chat-pane --> ion-card-content
  chat-pane --> ion-button
  chat-pane --> ion-header
  chat-pane --> chat-conversation
  chat-pane --> ion-footer
  chat-pane --> chat-input
  chat-message --> ion-item
  chat-message --> chat-message-status
  ion-item --> ion-icon
  ion-item --> ion-ripple-effect
  ion-item --> ion-note
  chat-message-status --> ion-icon
  chat-message-status --> chat-check-mark
  ion-card --> ion-ripple-effect
  ion-button --> ion-ripple-effect
  chat-conversation --> ion-content
  chat-conversation --> ion-list
  chat-input --> ion-item
  chat-input --> ion-textarea
  chat-input --> ion-icon
  style chat-pane fill:#f9f,stroke:#333,stroke-width:4px
```

----------------------------------------------

*Built with [StencilJS](https://stenciljs.com/)*
