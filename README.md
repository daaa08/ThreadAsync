## AsyncTask 🔄

- AsyncTask = Thread + Handler
- 스레드 간의 동기화와 핸들러 사용으로 복잡하고 번거로운 작업을 쉽게 만들어 줌
- 하나의 클래스에서 UI작업을 쉽게 하도록 해 줌
- 비교적 오래 걸리지않는 작업에 유용 ( 다운로드,새로고침... )
- But!!!!! 재사용이 불가능
~~~
1 execute()메소드를 통해 AsyncTask을 실행 (Thread의 start와 같음)
2 AsyncTask로 백그라운드 작업을 실행하기 전에 onPreExecute()실행 이 부분에는 이미지 로딩 작업이라면 로딩 중 이미지를 띄워 놓기 등, 스레드 작업 이전에 수행할 동작을 구현
3 새로 만든 스레드에서 백그라운드 작업을 수행 execute() 메소드를 호출할 때 사용된 파라미터를  전달 받음
4 doInBackground() 에서 중간 중간 진행 상태를 UI에 업데이트 하도록 하려면 publishProgress() 메소드를 호출
5 onProgressUpdate() 메소드는 publishProgress()가 호출 될 때 마다 자동으로 호출
6 doInBackground() 메소드에서 작업이 끝나면 onPostExecute() 로 결과 파라미터를 리턴하면서 그 리턴값을 통해 스레드 작업이 끝났을 때의 동작을 구현
** onPreExecute(), onProgressUpdate(), onPostExecute() 메인 스레드에서 실행되어 UI객체에 쉽게 접근 가능 **
~~~

![enter image description here](http://cfile23.uf.tistory.com/image/2420B240577D4A720F8136)

## AsyncTask<String, Integer, Float>
- 제네릭 타입
1 - doInBackground 의 인자
2 - onProgressUpdate 의 인자
3 - doInBackground 의 리턴타입
> Void는 아무것도 반환하지 않을때 사용

## onPreExecute
- doInBackground 시작 전에 호출
- 로딩바, Progress 같은 동작 중임을 알리는 작업
 ```java
 @Override
         protected void onPreExecute() {
             super.onPreExecute();
             progress.show();
         }
```

## doInBackground
- 실제 스레드 작업을 작성하는 곳
```java
@Override
            protected Float doInBackground(String... params) { // thread의 run과 같음
                try {
                    for(int i=0 ; i<10 ; i++) {
                        publishProgress(i*10); // onProgressUpdate 를 주기적으로 업데이트 해준다.
                        Thread.sleep(1000);
                    }

                    Thread.sleep(10000);
                    handler.sendEmptyMessage(MainActivity.SET_DONE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 1000.4f;
            }
```

## onPostExecute
- doInBackground 호출되고나서 호출됨
- 작업이 끝났음을 의미함
```java
@Override
            protected void onPostExecute(Float result) {
                // 결과값을 UI에 세팅
                setDone();
                progress.dismiss();
            }
```

## onProgressUpdate
- 주기적으로 doInBackground 에서 호출이 가능한 함수
```java
@Override
            protected void onProgressUpdate(Integer... values) {
                progress.setMessage("진행율 " +values[0]+ "%");
            }  // 퍼센티지 표시 됨
```

---
============ **참고** ==============

![enter image description here](http://img1.daumcdn.net/thumb/R1920x0/?fname=http%3A%2F%2Fcfile24.uf.tistory.com%2Fimage%2F277EDE4057D583F4173E0D)


> handler.sendEmptyMessage(MainActivity.SET_DONE);
⬇️ 와 같은 의미

Message msg = new Message();

msg.what = MainActivity.SET_DONE;

handler.sendMessage(msg);
