import setupStyles from "../stylesModules/setupStyles";
import { useState, useEffect, useRef } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client/dist/sockjs';
import ChatMessage from "../ProfilePage/components/service/ChatMessage";
import error from "eslint-plugin-react/lib/util/error.js";
import { useDispatch, useSelector } from "react-redux";
import { loadUser } from "../../components/redux/slices/authSlice";


const MessagesView =() => {

  const style = setupStyles("mainstyle")
  const auth = useSelector((state) => state.auth);
  const dispatch = useDispatch()
  const [messages, setMessages] = useState([]);
  const [client, setClient] = useState(null);
  const [connectionStatus, setConnectionStatus] = useState('Connecting...');
  const messageInputRef = useRef();
  const messagesEndRef = useRef(null);

  useEffect(() => {
    dispatch(loadUser(null));
  }, [dispatch]);

  useEffect(() => {
    const newClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      onConnect: () => {
        const joinMessage = {
          sender: auth.firstName,
          type: 'CONNECT',
        };
        newClient.publish({ destination: '/app/chat.add-user', body: JSON.stringify(joinMessage) });
        console.log(joinMessage); // Log the join message
        newClient.subscribe('/topic/public', message => {
          const newMessage = JSON.parse(message.body);
          setMessages(prevMessages => [...prevMessages, newMessage]); // Add the received message to the state
        });
        setConnectionStatus('Connected');
      },
      onDisconnect: () => {
        if (newClient.connected) { // Check if the client is connected
          const leaveMessage = {
            sender: auth.firstName,
            type: 'DISCONNECT',
          };
          newClient.publish({ destination: '/app/chat.add-user', body: JSON.stringify(leaveMessage) });
          console.log(leaveMessage); // Log the leave message
        }
        setConnectionStatus('Disconnected');
      },
      onWebSocketClose: () => {
        setConnectionStatus('Disconnected');
      },
      onWebSocketError: () => {
        console.error('WebSocket error: ', error);
        setConnectionStatus('Failed to connect');
      },
      onStompError: (frame) => {
        console.log('Broker reported error: ' + frame.headers['message']);
        console.log('Additional details: ' + frame.body);
      },
    });

    newClient.activate();
    setClient(newClient);
    return () => {
      newClient.deactivate();
    };
  }, []);

  useEffect(() => {
    // Scroll to the bottom whenever messages update
    messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
  }, [messages]);

  const sendMessage = () => {
    if (messageInputRef.current.value && client) {
      const chatMessage = {
        sender: auth.firstName,
        content: messageInputRef.current.value,
        type: 'CHAT',
      };
      client.publish({ destination: '/app/chat.send-message', body: JSON.stringify(chatMessage) });
      console.log(chatMessage);
      messageInputRef.current.value = '';
    } else {
      console.log('Unable to send message. The message is empty or the client is not connected.');
    }
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter' && !event.shiftKey) {
      sendMessage();
      event.preventDefault(); // Prevent form submission
    }
  };
   return (

<div className={style.tablemain}>
<form className={style.form_profile}>
<table className={style.tablemain}>
<div>
      <h2>{connectionStatus}</h2>
      <div display="flex" flexDirection="column" justifyContent="center" alignItems="center" mt={2}>
        <div sx={{height: '500px', overflow: 'auto', width: '100%'}}>
          {messages.map((message, index) => (
            <ChatMessage key={index} message={message} username={auth.firstName} />
          ))}
          <div ref={messagesEndRef}/>
        </div>
        <div display="flex" justifyContent="center" alignItems="stretch" mt={2}>
          <div
            sx={{
              color: 'white', '& .MuiOutlinedInput-notchedOutline': {borderColor: 'gray'},
              width: '300px',
              height: '10px',
              '& .MuiOutlinedInput-root': {
                borderRadius: '36px',
                '& fieldset': {
                  borderColor: 'gray',
                },
                '& input': {
                  height: '10px',
                },
              },
            }}
            inputProps={{style: {color: 'white'}}}
            inputRef={messageInputRef}
            variant="outlined"
            placeholder="Type a message..."
            onKeyDown={handleKeyDown}
          />
          <div marginLeft={2}>
            <button
              variant="contained"
              color="primary"
              sx={{
                width: '94px',
                height: '42px',
                borderRadius: '36px',
              }}
              onClick={sendMessage}>
              Send
            </button>
          </div>
        </div>
      </div>
    </div>
</table>

</form>
 </div>
   )
}

export default MessagesView;

