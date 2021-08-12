<template >
  <div id="bg">
    <div class="wrapper flex">
      <div class="cols">

        <!-- 알림장 -->
        <div class="col-4" ontouchstart="this.classList.toggle('hover');">
          <div class="container" @click="$router.push({ name: 'Notebook' })">
            <div class="front" style="background-image: url(https://i.pinimg.com/564x/43/89/c8/4389c82649e8503bc9ecea9c6d88dc48.jpg)">
              <div class="inner">
                <p>알림장</p>
                <span>Notebook</span>
              </div>
            </div>
            <div class="back">
              <div class="inner" v-if="user.type === 1">
                <p>선생님께서 보내신</p>
                <p>알림장을</p>
                <p>확인할 수 있습니다</p>
              </div>              
              <div class="inner" v-else>
                <p>학부모님께</p>
                <p>알림장을</p>
                <p>전달할 수 있습니다</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 채팅 -->
        <div class="col-4" ontouchstart="this.classList.toggle('hover');">
          <div class="container" @click="$router.push({ name: 'Chat' })">
            <div class="front" style="background-image: url(https://i.pinimg.com/564x/36/7a/5e/367a5e3464a16f6413efffb77f377beb.jpg)">
              <div class="inner">
                <p>채팅</p>
                <span>Chat</span>
              </div>
            </div>
            <div class="back">
              <div class="inner" v-if="user.type === 1">
                <p>우리 반 선생님,</p>
                <p>학부모님과</p>
                <p>대화를 나눠보세요</p>
              </div>              
              <div class="inner" v-else>
                <p>우리 반 선생님,</p>
                <p>학부모님과</p>
                <p>대화를 나눠보세요</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 앨범 -->
        <div class="col-4" ontouchstart="this.classList.toggle('hover');">
          <div class="container" @click="$router.push({ name: 'Album' })">
            <div class="front" style="background-image: url(https://i.pinimg.com/564x/0f/a8/19/0fa819101ad9c4fc68c0116fd8996eee.jpg)">
              <div class="inner">
                <p>앨범</p>
                <span>Album</span>
              </div>
            </div>
            <div class="back">
              <div class="inner" v-if="user.type === 1">
                <p>우리 아이의</p>
                <p>활동 사진을 열람하고</p>
                <p>다운로드할 수 있습니다</p>
              </div>              
              <div class="inner" v-else>
                <p>학부모님께</p>
                <p>우리 반 사진을</p>   
                <p>전달할 수 있습니다</p>
              </div>
            </div>
          </div>
        </div>

        <!-- 출석 -->
        <div class="col-4" ontouchstart="this.classList.toggle('hover');">
          <div class="container" @click="$router.push({ name: 'Attendance' })">
            <div class="front" style="background-image: url(https://i.pinimg.com/564x/48/77/37/487737d3e7756cd2c601652cc96edbcc.jpg)">
              <div class="inner">
                <p>출석</p>
                <span>Attendance</span>
              </div>
            </div>
            <div class="back">
              <div class="inner" v-if="user.type === 1">
                <p>우리 아이의</p>
                <p>출석 상태를</p>
                <p>확인할 수 있습니다</p>
              </div>              
              <div class="inner" v-else>
                <p>반 아이들의</p>
                <p>출석 상태를</p>
                <p>관리할 수 있습니다</p>    
              </div>
            </div>
          </div>
        </div>

        <!-- 공지 -->
        <div class="col-4" ontouchstart="this.classList.toggle('hover');">
          <div class="container" @click="$router.push({ name: 'Notice' })">
            <div class="front" style="background-image: url(https://i.pinimg.com/564x/24/4b/4a/244b4a61bcaf23734ba4199bc2e1ef1a.jpg)">
              <div class="inner">
                <p>공지</p>
                <span>Notice</span>
              </div>
            </div>
            <div class="back">
              <div class="inner" v-if="user.type === 1">
                <p>유치원의</p>
                <p>공지, 일정, 식단을</p>
                <p>확인할 수 있습니다</p>
              </div>              
              <div class="inner" v-else>
                <p>학부모님께</p>
                <p>공지, 일정, 식단을</p>
                <p>전달할 수 있습니다</p>   
              </div>
            </div>
          </div>
        </div>

        <!-- 요청 -->
        <div class="col-4" ontouchstart="this.classList.toggle('hover');">
          <div class="container" @click="$router.push({ name: 'Request' })">
            <div class="front" style="background-image: url(https://i.pinimg.com/564x/90/e3/e7/90e3e7378f15a1c3854898934e7a8acf.jpg)">
              <div class="inner">
                <p>요청</p>
                <span>Request</span>
              </div>
            </div>
            <div class="back">
              <div class="inner" v-if="user.type === 1">
                <p>담임 선생님께</p>
                <p>투약 요청, 귀가 동의를</p>
                <p>전달할 수 있습니다</p>
              </div>              
              <div class="inner" v-else>
                <p>학부모님께서 보내신</p>
                <p>투약 요청, 귀가 동의를</p>
                <p>확인할 수 있습니다</p>    
              </div>
            </div>
          </div>
        </div>

          
      </div>
    </div>
  </div>  
</template>

<script>
import { mapState } from 'vuex'
export default {
  name: "MainPage",
  data: () => {
    return {
      funcName: [
        "Notebook",
        "Chat",
        "Album",
        "Attendance",
        "Notice",
        "Request",
      ],
    };
  },
  computed: {
    // 배열안에 ['videos']로 쓴 경우는 이름을 바꿀 수 없음 
    ...mapState({
      'user': 'user',
    })
  }
};
</script>

<style scoped>
* {
  font-family: 'NanumSquareRound';
}
#bg {
  background-color: rgba(102,122,188, 0.1);
  width: 100%;
  height: 100%;
}
p {
  font-size: 1rem;
  color: rgba(40, 40, 40, 0.8);
}
.wrapper{
  position: relative;
  top: 8%;
  width: 90%;
  margin: 0 auto;
  max-width: 60rem;
}

.cols{
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -ms-flex-wrap: wrap;
      flex-wrap: wrap;
  -webkit-box-pack: center;
      -ms-flex-pack: center;
          justify-content: center;
}

.col{
  width: calc(25% - 2rem);
  margin: 1rem;
  cursor: pointer;
}

.container{
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
  -webkit-perspective: 1000px;
          perspective: 1000px;
}

.front,
.back{
  background-size: cover;
  box-shadow: 0 4px 8px 0 rgba(0,0,0,0.25);
  border-radius: 10px;
  background-position: center;
  -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
  -webkit-backface-visibility: hidden;
          backface-visibility: hidden;
  text-align: center;
  min-height: 280px;
  height: auto;
  border-radius: 10px;
  color: rgba(256, 256, 256, 0.85);
  font-size: 1.5rem;
}

.back{
  color: rgba(40, 40, 40);
  background: rgb(229, 229, 229)
}

.front:after{
  position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
    width: 100%;
    height: 100%;
    content: '';
    display: block;
    opacity: .02;
    background-color: #000;
    -webkit-backface-visibility: hidden;
            backface-visibility: hidden;
    border-radius: 10px;
}
.container:hover .front,
.container:hover .back{
    -webkit-transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    -o-transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
    transition: transform .7s cubic-bezier(0.4, 0.2, 0.2, 1), -webkit-transform .7s cubic-bezier(0.4, 0.2, 0.2, 1);
}

.back{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
}

.inner{
    -webkit-transform: translateY(-50%) translateZ(60px) scale(0.94);
            transform: translateY(-50%) translateZ(60px) scale(0.94);
    top: 50%;
    position: absolute;
    left: 0;
    width: 100%;
    padding: 2rem;
    -webkit-box-sizing: border-box;
            box-sizing: border-box;
    outline: 1px solid transparent;
    -webkit-perspective: inherit;
            perspective: inherit;
    z-index: 2;
}

.container .back{
    -webkit-transform: rotateY(180deg);
            transform: rotateY(180deg);
    -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
}

.container .front{
    -webkit-transform: rotateY(0deg);
            transform: rotateY(0deg);
    -webkit-transform-style: preserve-3d;
            transform-style: preserve-3d;
}

.container:hover .back{
  -webkit-transform: rotateY(0deg);
          transform: rotateY(0deg);
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
}

.container:hover .front{
  -webkit-transform: rotateY(-180deg);
          transform: rotateY(-180deg);
  -webkit-transform-style: preserve-3d;
          transform-style: preserve-3d;
}

.front .inner p{
  font-size: 1.8rem;
  margin-bottom: 2rem;
  position: relative;
}

.front .inner p:after{
  content: '';
  width: 50%;
  height: 0.1rem;
  position: absolute;
  background: #C6D4DF;
  display: block;
  left: 0;
  right: 0;
  margin: 0 auto;
  bottom: -.75rem;
}

.front .inner span{
  color: rgb(40, 40, 40);
  font-size: 0.9rem;
  font-weight: 300;
}

@media screen and (max-width: 64rem){
  .col{
    width: calc(33.333333% - 2rem);
  }
}

@media screen and (max-width: 48rem){
  .col{
    width: calc(50% - 2rem);
  }
}

@media screen and (max-width: 32rem){
  .col{
    width: 100%;
    margin: 0 0 2rem 0;
  }
}
</style>
