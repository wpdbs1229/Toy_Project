
//-----------------CountDown--------------------------//

// 결혼 날짜를 countDownDate에 저장
var countDownDate = new Date("Jan 15, 2023 14:00:00")
// 1초마다 갱신
var x = setInterval(function() {

  // 오늘 날짜를 얻어온다
  var now = new Date().getTime();

  // 결혼날짜에서 오늘 날짜를 뺴 차이를 계산
  var distance = countDownDate - now;

  // 계산된 차이시간을 각각 일, 시간, 분, 초로 나눈다. 
  var days = Math.floor(distance / (1000 * 60 * 60 * 24));
  var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
  var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
  var seconds = Math.floor((distance % (1000 * 60)) / 1000);

  // 각 ID에 맞게 데이터가 들어가도록 한다
  document.getElementById("days").innerText = days,
  document.getElementById("hours").innerText = hours,
  document.getElementById("minutes").innerText = minutes,
  document.getElementById("seconds").innerText = seconds;

  // 만약  시간의 차이가 0이면 EXPIRED되었다고 뜨게한다.
  if (distance < 0) {
    clearInterval(x);
    document.getElementById("#countdown").innerHTML = "EXPIRED";
  }
}, 1000);




//-------------------------- my Song--------------------//

// 태그를 저장
var mySong = document.getElementById("mySong");  
var icon =document.getElementById("icon");

// 이벤트 발생
icon.onclick =function(){

  //일시정지 상태일 때, 버튼을 누르면, 음악이 켜지고, 아이콘이 pause로 바뀐다
  if(mySong.paused){
    mySong.play();
    icon.src ="imgs/pause.png";
  }
  //재생 상태일 때, 버튼을 누르면, 음악이 정지하고, 아이콘이 play로 바뀐다
  else{
    mySong.pause();
    icon.src ="imgs/play.png";
  }
}


//-------------복사 기능----------------//
function CopyFunction() {
  
  // 복사 영역을 선택 
  var copyText = document.getElementById("account");

  // 데스크탑, 모바일에 맞게 복사영역 설정
  copyText.select();
  copyText.setSelectionRange(0, 99999); // 모바일 장치를 위함

   // 클립보드에 복사할 text를 저장
  navigator.clipboard.writeText(copyText.value);

  // 복사된 text를 알림창에 띄어준다.
  alert("Copied the text: " + copyText.value);
}