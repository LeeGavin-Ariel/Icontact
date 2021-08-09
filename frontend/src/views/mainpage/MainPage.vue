<template >
  <div id="bg">
    <!-- 동그라미 -->
    <div class="inner-header flex ">
      <div class="links">
        <ul class="links__list" style="--item-total: 6">
          <li class="links__item" style="--item-count: 1">
            <button
              class="links__link"
              @click="$router.push({ name: 'Notice' })"
            >
              <img
                class="links__icon"
                src="@/assets/flaticon/toys.png"
                alt=""
              />
              <span class="links__text">공지사항</span>
            </button>
          </li>
          <li class="links__item" style="--item-count: 2">
            <button
              class="links__link"
              @click="$router.push({ name: 'Request' })"
            >
              <img
                class="links__icon"
                src="@/assets/flaticon/toys.png"
                alt=""
              />
              <span class="links__text">요청사항</span>
            </button>
          </li>
          <li class="links__item" style="--item-count: 3">
            <button class="links__link" @click="$router.push({ name: 'Chat' })">
              <img
                class="links__icon"
                src="@/assets/flaticon/toys.png"
                alt=""
              />
              <span class="links__text">채팅</span>
            </button>
          </li>
          <li class="links__item" style="--item-count: 4">
            <button
              class="links__link"
              @click="$router.push({ name: 'Album' })"
            >
              <img
                class="links__icon"
                src="@/assets/flaticon/toys.png"
                alt=""
              />
              <span class="links__text">앨범</span>
            </button>
          </li>
          <li class="links__item" style="--item-count: 5">
            <button
              class="links__link"
              @click="$router.push({ name: 'Notebook' })"
            >
              <img class="links__icon" src="@/assets/flaticon/toys.png" alt="" />
              <span class="links__text">알림장</span>
            </button>
          </li>
          <li class="links__item" style="--item-count: 6">
            <button
              class="links__link"
              @click="$router.push({ name: 'Attendance' })"
            >
              <img class="links__icon" src="@/assets/flaticon/toys.png" alt="" />
              <span class="links__text">출석</span>
            </button>
          </li>
        </ul>
      </div>

      <div>
        <svg
          class="waves"
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          viewBox="0 24 150 28"
          preserveAspectRatio="none"
          shape-rendering="auto"
        >
          <defs>
            <path
              id="gentle-wave"
              d="M-160 44c30 0 58-18 88-18s 58 18 88 18 58-18 88-18 58 18 88 18 v44h-352z"
            />
          </defs>
          <g class="parallax">
            <use
              xlink:href="#gentle-wave"
              x="48"
              y="0"
              fill="rgba(255,255,255,0.7)"
            />
            <use
              xlink:href="#gentle-wave"
              x="48"
              y="3"
              fill="rgba(255,255,255,0.5)"
            />
            <use
              xlink:href="#gentle-wave"
              x="48"
              y="5"
              fill="rgba(255,255,255,0.3)"
            />
            <use xlink:href="#gentle-wave" x="48" y="7" fill="#fff" />
          </g>
        </svg>
      </div>
    </div>
    <!--Waves Container-->
    <!--Waves end-->
  </div>

  <!--  
  <div style="height:100vh" class="d-flex align-center">
    <v-container id="container" >
      <v-row>
        <v-col
          v-for="func in funcName"
          :key="func"
          class="d-flex child-flex"
          cols="4"
        >

          <button @click="$router.push({ name: func })">
            <v-img
              :src="require(`@/assets/${func}.png`)"
              :lazy-src="require(`@/assets/${func}.png`)"
              aspect-ratio="1"
              class="grey lighten-2 img"
            >
              <template v-slot:placeholder>
                <v-row
                  class="fill-height ma-0"
                  align="center"
                  justify="center"
                >
                  <v-progress-circular
                    indeterminate
                    color="grey lighten-5"
                  ></v-progress-circular>
                </v-row>
              </template>
            </v-img>
          </button>
        </v-col>
      </v-row>
    </v-container>
  </div>
  -->
</template>

<script>
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
};
</script>

<style >
#bg {
  background-color: #FFE495;
  width: 100%;
  height: 100vw;
}
/* #container {
  width: 800px;
} */
@font-face {
  font-family: "TmoneyRoundWindExtraBold";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-07@1.0/TmoneyRoundWindExtraBold.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}

:root {
  --base-grid: 8px;
  --colour-white: #ffffff;
  --colour-black: #1a1a1a;
}

.links {
  --link-size: calc(var(--base-grid) * 20);
  color: var(--colour-black);
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  min-height: 70%;
  z-index: 10000;
}

.links__list {
  position: relative;
  list-style: none;
}

.links__item {
  width: var(--link-size);
  height: var(--link-size);
  position: absolute;
  top: 0;
  left: 0;
  margin-top: calc(var(--link-size) / -2);
  margin-left: calc(var(--link-size) / -2);
  --angle: calc(360deg / var(--item-total));
  --rotation: calc(150deg + var(--angle) * var(--item-count));
  transform: rotate(var(--rotation))
    translate(calc(var(--link-size) + var(--base-grid) * 2))
    rotate(calc(var(--rotation) * -1));
}

.links__link {
  opacity: 0;
  animation: on-load 0.3s ease-in-out forwards;
  animation-delay: calc(var(--item-count) * 150ms);
  width: 100%;
  height: 100%;
  border-radius: 50%;
  position: relative;
  background-color: var(--colour-white);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-decoration: none;
  color: inherit;
}

.links__icon {
  width: calc(var(--base-grid) * 11);
  height: calc(var(--base-grid) * 11);
  transition: all 0.3s ease-in-out;
  fill: var(--colour-black);
}

.links__text {
  position: absolute;
  width: 100%;
  left: 0;
  text-align: center;
  height: calc(var(--base-grid) * 2);
  font-size: calc(var(--base-grid) * 2);
  display: none;
  bottom: calc(var(--base-grid) * 8.5);
  animation: text 0.3s ease-in-out forwards;
  color: white;
  font-family: "TmoneyRoundWindExtraBold";
}

.links__link:after {
  content: "";
  background-color: transparent;
  width: var(--link-size);
  height: var(--link-size);
  border: 2px dashed var(--colour-white);
  display: block;
  border-radius: 50%;
  position: absolute;
  top: 0;
  left: 0;
  transition: all 0.3s cubic-bezier(0.53, -0.67, 0.73, 0.74);
  transform: none;
  opacity: 0;
}

.links__link:hover .links__icon {
  transition: all 0.3s ease-in-out;
  transform: translateY(calc(var(--base-grid) * -1));
}

.links__link:hover .links__text {
  display: block;
}

.links__link:hover:after {
  transition: all 0.3s cubic-bezier(0.37, 0.74, 0.15, 1.65);
  transform: scale(1.1);
  opacity: 1;
}

@keyframes on-load {
  0% {
    opacity: 0;
    transform: scale(0.3);
  }
  70% {
    opacity: 0.7;
    transform: scale(1.1);
  }
  100% {
    opacity: 1;
    transform: scale(1);
  }
}

@keyframes text {
  0% {
    opacity: 0;
    transform: scale(0.3) translateY(0);
  }
  100% {
    opacity: 1;
    transform: scale(1) translateY(calc(var(--base-grid) * 5));
  }
}

.inner-header {
  height: 100%;
  width: 100%;
  margin: 0;
  padding: 0;
}

.flex {
  /*Flexbox for containers*/
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.waves {
  position: absolute;
  align-items: flex-end;
  left:0;
  bottom: 0;
  width: 100vw;
  height: 100vh;
  /*Fix for safari gap*/
  min-height: 130px;
  max-height: 150px;
}

/* Animation */

.parallax > use {
  animation: move-forever 25s cubic-bezier(0.55, 0.5, 0.45, 0.5) infinite;
}
.parallax > use:nth-child(1) {
  animation-delay: -2s;
  animation-duration: 7s;
}
.parallax > use:nth-child(2) {
  animation-delay: -3s;
  animation-duration: 10s;
}
.parallax > use:nth-child(3) {
  animation-delay: -4s;
  animation-duration: 13s;
}
.parallax > use:nth-child(4) {
  animation-delay: -5s;
  animation-duration: 20s;
}
@keyframes move-forever {
  0% {
    transform: translate3d(-90px, 0, 0);
  }
  100% {
    transform: translate3d(85px, 0, 0);
  }
}
/*Shrinking for mobile*/
@media (max-width: 768px) {
  .waves {
    height: 40px;
    min-height: 40px;
  }
  h1 {
    font-size: 24px;
  }
}



</style>
