let date = new Date("Jan 15, 2023 14:00:00");

const renderCalendar = () => {
    const viewYear = date.getFullYear();
    const viewMonth = date.getMonth();

    //제목 년, 월을 왼쪽 상단에 표시
    document.querySelector('.view-year-month').textContent = (viewYear)+"."+(viewMonth+1);



    //지난달의 마지막 날
    const prevLast = new Date(viewYear, viewMonth, 0);
    //이번 달의 마지막 날
    const thisLast = new Date(viewYear, viewMonth + 1, 0);

    //지난달 마지막 날짜와 요일
    const PLDate = prevLast.getDate();
    const PLDay = prevLast.getDay();

    //이번 달 마지막 날짜와 요일
    const TLDate = thisLast.getDate();
    const TLDay = thisLast.getDay();

    const prevDates = [];
    const thisDates = [...Array(TLDate+1).keys()].slice(1);
    const nextDates = [];


    //지나간 달이 이번 달에 보여지게 표시,, 토요일이면 표시x
    if(PLDay !== 6){
        //마지막 날짜 부터 1씩 줄어들게
        for(let i=0; i<PLDay+1; i++){
            prevDates.unshift(PLDate-i);
        }
    }

    //마지막 날짜의 요일을 기준으로 nextDates배열에 다음달날짜를 채움
    for(let i =1; i<7-TLDay; i++){
        nextDates.push(i);
    }

    // 3개의 배열을 합침 prevDates + thisDates + nextDates
    const dates = prevDates.concat(thisDates,nextDates);

    //Dates 정리
    const firstDateIndex = dates.indexOf(1);
    const lastDateIndex = dates.lastIndexOf(TLDate);

    dates.forEach((date, i) => {
        const condition = i >= firstDateIndex && i<lastDateIndex+1
            ? 'this'
            : 'other';
        dates[i] = '<div class = "date"><span class ="'+ condition +'">'+date+"</span></div>";
    });

    //Dates 그리기
    document.querySelector('.dates').innerHTML =dates.join('');

//오늘 날짜 그리기 today 데이터와 viewMonth와 viewYear같은지 비교
    const marriage = new Date("Jan 15, 2023 14:00:00");
    if(viewMonth=== marriage.getMonth() && viewYear===marriage.getFullYear()){
        for(let date of document.querySelectorAll('.this')){ // this 클래스 가진 태그들을 다찾은후
            if(+date.innerText === marriage.getDate()){ //해당태그가 문자열이므로 +연산자를 통해 숫자로변경
                date.classList.add('marriage');//classlist에 today를  추가
                break;
            }
        }
}
   
};

renderCalendar();
//지난 달로 이동
const prevMonth = () =>{
    date.setDate(1);
    date.setMonth(date.getMonth()-1);
    renderCalendar();
}
//다음 달로 이동
const nextMonth = () =>{
    date.setDate(1);
    date.setMonth(date.getMonth()+1);
    renderCalendar();
}
//오늘 날짜로 이동
const goMarriage = () => {
    date =new Date("Jan 15, 2023 14:00:00");
    renderCalendar();
}

