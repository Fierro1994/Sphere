function ChatMessage({ message, username }) {
  return (
   <>
      <h4>{message.sender}</h4>
    
      
    <p>{message.content}</p>
   </>
  );
}

export default ChatMessage;