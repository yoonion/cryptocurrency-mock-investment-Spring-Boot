// 웹소켓 생성
const upbitWebsocketUrl = "wss://api.upbit.com/websocket/v1";
const socket = new WebSocket(upbitWebsocketUrl);

// 커넥션이 제대로 생성되었을 때
socket.onopen = function (e) {
    console.log("웹 소켓 연결 성공")
    let jsonData = [
        {
            "ticket": "test example"
        },
        {
            "type": "ticker", // 현재가
            "codes": marketCodeListForWebsocket // ["KRW-BTC", "KRW-NEO" ... ] - order.html 에서 전역으로 넘어온 값
        },
        /*{
            "type": "trade", // 체결
            "codes": [[${marketListString}]] // ["KRW-BTC", "KRW-NEO" ... ]
        }*/
    ];

    socket.send(JSON.stringify(jsonData))
};

// 데이터를 수신 받았을 때
socket.onmessage = async function (e) {
    try {
        if (e !== null && e !== undefined) {
            let result = JSON.parse(await e.data.text()); // blob to json

            // 웹소켓 type 구분 - ticker(현재가), trade(체결)
            let type = result.type;

            // 암호화폐 마켓 코드
            let crypto_code = result.code; // ex) KRW-BTC, KRW-NEO ...

            // =============================================
            // ticker(현재가) 응답
            // =============================================
            if (type === 'ticker') {
                // 현재가
                let trade_price = result.trade_price;
                trade_price = new Intl.NumberFormat('ko-kr').format(trade_price);

                // 전일 대비 등락율(%)
                let signed_change_rate = (result.signed_change_rate * 100 ).toFixed(2);

                if (signed_change_rate > 0) { // 전일 대비 상승일 경우 style
                    signed_change_rate = "+" + signed_change_rate; // 부호 + 붙여주기
                    document.getElementById(crypto_code + '-trade_price').style.color = '#0ea600';
                    document.getElementById(crypto_code + '-signed_change_rate').style.color = '#0ea600';
                } else if (signed_change_rate == 0) { //  전일 대비 같을 경우
                    document.getElementById(crypto_code + '-trade_price').style.color = 'gray';
                    document.getElementById(crypto_code + '-signed_change_rate').style.color = 'gray';
                } else if (signed_change_rate < 0) { // 전일 대비 하락인 경우
                    document.getElementById(crypto_code + '-trade_price').style.color = '#F6465D';
                    document.getElementById(crypto_code + '-signed_change_rate').style.color = '#F6465D';
                }

                // 24시간 누적 거래대금
                let acc_trade_price_24h = Math.ceil(result.acc_trade_price_24h);
                acc_trade_price_24h = Math.round(acc_trade_price_24h / 1000000) * 1000000;
                acc_trade_price_24h = acc_trade_price_24h.toString();
                acc_trade_price_24h = acc_trade_price_24h.slice(0, acc_trade_price_24h.length-6) + "백만";

                // 해당하는 암호화폐 html text 변경해준다
                document.getElementById(crypto_code + '-trade_price').innerText = trade_price;
                document.getElementById(crypto_code + '-signed_change_rate').innerText = signed_change_rate + "%";
                document.getElementById(crypto_code + '-acc_trade_price_24h').innerText = acc_trade_price_24h;
                // console.log(crypto_code + "현재가 : " + trade_price, "전일 대비 등락율" + signed_change_rate, "24시간 누적 거래대금 : " + acc_trade_price_24h);
            }
                // =============================================
                // trade(체결) 응답
            // =============================================
            else if (type === 'trade') {

                /*let ask_bid = result.ask_bid; // 매수/매도 구분 - ASK : 매도 / BID : 매수
                //console.log(ask_bid);

                if (ask_bid === 'ASK') {
                    console.log(crypto_code + " 매수")
                    document.getElementById(crypto_code + '-trade_price').classList.add('buy-highlight');
                    // document.getElementById(crypto_code + '-trade_price').classList.remove('buy-highlight');

                } else if (ask_bid === 'BID'){
                    console.log(crypto_code + " 매도")
                    document.getElementById(crypto_code + '-trade_price').classList.add('sell-highlight');
                    // document.getElementById(crypto_code + '-trade_price').classList.remove('sell-highlight');
                }*/

            }

        }
    } catch (err) {
        console.log("ERROR !!" + err);
    }
};

// 에러가 발생했을 때
socket.onerror = function (e) {
    console.log("업비트 웹소켓 연결 실패");
    console.log(e);
};

// 암호화폐별 주문 정보 가져와 변경(비동기)
let getCryptoOrderInfo = (market_code) => {

    const xhr = new XMLHttpRequest();
    xhr.open("GET", "/trade/order/" + market_code);
    xhr.send();

    xhr.onload = () => {
        let response_status_code = xhr.status;
        let responseHtmlString = xhr.response;   // 요청에 대한 응답

        if (response_status_code === 200) {
            // let order_info = document.getElementById('order_info');
            // order_info.insertAdjacentHTML("afterend", responseHtmlString);
            $('#order_info').html(responseHtmlString);
        }
    };

    xhr.onerror = () => {
        console.log('xhr error !!');
    };

}