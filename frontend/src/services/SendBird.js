import { requestSendBirdPost } from '../utils/request'

const SendBird = require('sendbird')

//여기에 본인 app_id입력
const sendBird = new SendBird({
  appId: '702BDEC1-CAED-42B4-897E-DFBD4B6DA1E9'
})
const ChannelHandler = new sendBird.ChannelHandler()

export default {

  async addUser(userId, nickName){
    console.log("sendbird 연결 들어옴")
    console.log(userId)
    console.log(nickName)

    let url = 'https://api-702BDEC1-CAED-42B4-897E-DFBD4B6DA1E9.sendbird.com/v3/users';
    let headers = {
      'Api-Token':'f9cf3243b0c3e22b4c4127db152ed31ab58e58ea',
    }
    let data = {
      "user_id": userId,
      "nickname": nickName,
      "profile_url": ""
    }


    console.log('요청전 데이터 확인');
    console.log(url);
    console.log(headers);
    console.log(data);

    var result = await requestSendBirdPost(url, data, headers);

    console.log(result);

    return new Promise((resolve, reject) => {
        if (reject)
        resolve(result)
    })
    // return this.login(data.user_id);

  },

  //로그인을 하면 connect를 한다.
  login (userId) {
    return new Promise((resolve, reject) => {
      //커넥트를 한다. userId으로 resolve는 뭐지?
      return sendBird.connect(userId, (user, error) => {
        if (error) reject(error)
        resolve(user)
      })
    })

  },
  
  //연결 해제
  disconnect(){
    sendBird.disconnect();
  },

  //그룹채널 추가
  createGroupChannel(userIds) {
    return new Promise((resolve, reject) => {
      let params = new this.sb.GroupChannelParams();
      params.addUserIds(userIds);
      return sendBird.GroupChannel.createChannel(params, (groupChannel, error) => {
        error ? reject(error) : resolve(groupChannel);
      });
    });
  },

  //유저 아이디 조합으로, 그룹채널 생성
  createGroupChannelWithUserIds(ME, YOU, NAME, COVER_URL, DATA){
    var userIds = [ME, YOU];

    sendBird.GroupChannel.createChannelWithUserIds(userIds, true, NAME, COVER_URL, DATA, function(groupChannel, error) {
        if (error) {
            // Handle error.
            console.log(error);
        }
        console.log('채널이 생성되었습니다.')
    });
  },



  getChannel (channelUrl) {

    return new Promise((resolve, reject) => {
      return sendBird.GroupChannel.getChannel(channelUrl, (groupChannel, error) => {
        error ? reject(error) : resolve(groupChannel);
      });
    });

  },

  exitChannel (channelUrl) {

    return new Promise((resolve, reject) => {
      return sendBird.OpenChannel.getChannel(channelUrl, (channel, error) => {
        if (error) reject(error)
        channel.exit()
        resolve(channel)
      })
    })

  },

  getChannelMessages (channel, messageNumber) {

    const messageListQuery = channel.createPreviousMessageListQuery()
    messageNumber = messageNumber || 10

    return new Promise((resolve, reject) => {
      return messageListQuery.load(messageNumber, false, (messageList, error) => {
        if (error) reject(error)
        resolve(messageList)
      })
    })
  },

  getPreviousMessages(channel, earliestMessageTimestamp, limit) {
    const messageListQuery = channel.createMessageListQuery()

    return new Promise((resolve, reject) => {
      return messageListQuery.prev(earliestMessageTimestamp, limit, false, (messageList, error) => {
        if (error) reject(error)
        resolve(messageList)
      })
    })
  },

  //그룹채팅 이전 채팅목록 가져오기
  getPreviousGroupMessages(groupChannel) {
    // There should only be one single instance per channel view.
    var listQuery = groupChannel.createPreviousMessageListQuery();
    listQuery.limit = 20;
    listQuery.reverse = false;
    listQuery.includeMetaArray = false;  // Retrieve a list of messages along with their metaarrays.
    listQuery.includeReaction = false;   // Retrieve a list of messages along with their reactions.


    return new Promise((resolve, reject) => {
      // Retrieving previous messages.
      listQuery.load(function(messages, error) {
          if (error) {
              // Handle error.
              reject(error)
          }
          resolve(messages)
      });
    })
  },


  getChannelList() {

    const openChannelListQuery = sendBird.OpenChannel.createOpenChannelListQuery()

    return new Promise((resolve, reject) => {
      return openChannelListQuery.next((channels, error) => {
        if (error) reject(error)
        resolve(channels)
      })
    })
  },

  //그룹채널 리스트 불러오기
  getGroupChannelList(){
    var listQuery = sendBird.GroupChannel.createMyGroupChannelListQuery();
    listQuery.includeEmpty = true;
    listQuery.memberStateFilter = 'joined_only';    // 'all', 'joined_only', 'invited_only', 'invited_by_friend', and 'invited_by_non_friend'
    listQuery.order = 'latest_last_message';    // 'chronological', 'latest_last_message', 'channel_name_alphabetical', and 'metadata_value_alphabetical'
    listQuery.limit = 20;   // The value of pagination limit could be set up to 100.

    return new Promise((resolve, reject) => {
      return listQuery.next((channels, error) => {
        if (error) reject(error)
        resolve(channels)
      })
    })
  },

    //그룹채널 닉네임 검색 불러오기
    getGroupChannelListByNickname(searchNickname){
      var listQuery = sendBird.GroupChannel.createMyGroupChannelListQuery();
      listQuery.includeEmpty = true;
      listQuery.nicknameContainsFilter = searchNickname;    
      listQuery.order = 'latest_last_message';    // 'chronological', 'latest_last_message', 'channel_name_alphabetical', and 'metadata_value_alphabetical'
      listQuery.limit = 15;   // The value of pagination limit could be set up to 100.
  
      return new Promise((resolve, reject) => {
        return listQuery.next((channels, error) => {
          if (error) reject(error)
          resolve(channels)
        })
      })
    },

  //채널 유저목록 조회
  getChannelUsers(channel) {

    const memberListQuery = channel.createMemberListQuery()

    return new Promise((resolve, reject) => {
      return memberListQuery.next((participantList, error) => {
        if (error) reject(error)
        resolve(participantList)
      })
    })

  },

  sendMessage(channel, message) {
    console.log(channel);
    console.log(message);

    return new Promise((resolve, reject) => {
      return channel.sendUserMessage(message, (message, error) => {
        if (error) reject(error)
        resolve(message)
      })
    })

  },

  onMessageReceived(channel, cb) {

    ChannelHandler.onMessageReceived = (channel, message) => {
      cb(channel, message)
    }

    sendBird.addChannelHandler(channel, ChannelHandler)

  },

  onUserEntered(channel, cb) {

    ChannelHandler.onUserEntered = (channel, user) => {
      cb(channel, user)
    }

    sendBird.addChannelHandler(channel, ChannelHandler)

  },

  onUserExited(channel, cb) {

    ChannelHandler.onUserExited = (channel, user) => {
      cb(channel, user)
    }

    sendBird.addChannelHandler(channel, ChannelHandler)

  },

}
