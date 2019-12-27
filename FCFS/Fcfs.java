/*
*@author Hyein Lee, Minji Jung
*@since 2019.12.20
*/

import java.util.Scanner;//사용자의 입력을 받기위해 Scanner를 import함

//======================Process Setting======================
abstract class ProcessSetting{
  public int processname; //프로세스 이름(p1, p2, p3)
  public int as;          //프로세스 도착 순서(Arrival Sequence, only 1,2,3)
  public int at;          //프로세스 도착 시간(Arrival Time)
  public int pt;          //프로세스 실행 시간(Processing Time, Working time, Burst Time)
  public int wt;          //프로세스가 기다리는 시간(Wait Time)
  public int it;          //프로세스가 실행된 시간(Ing Time, the time which process used)
  public int rt;          //프로세스 남은 시간(Rest Time ==pt-it)
  public int responsetime;//반환 시간

  ProcessSetting(){};
  ProcessSetting(int pn,int as,int at,int pt){//ProcessSetting-프로세스 세팅
    this.processname=pn;//프로세스 이름
    this.as=as;         //프로세스 도착 순서
    this.at=at;         //프로세스 도착 시간
    this.pt=pt;         //프로세스 실행 시간
  }
}

//======================MakeProcess=========================
class Mp1 extends ProcessSetting{            //Mp1은 ProcessSetting함수를 상속받아 사용
  public Mp1(int pn, int as, int at, int pt){//ProcessSetting과 같은 인자를 씀
    super(pn,as,at,pt);                      //부모 클래스의 생성자를 호출
  }
}
class Mp2 extends ProcessSetting{           //Mp2은 ProcessSetting함수를 상속받아 사용
  public Mp2(int pn,int as, int at, int pt){//ProcessSetting과 같은 인자를 씀
    super(pn,as,at,pt);                      //부모 클래스의 생성자를 호출
  }
}
class Mp3 extends ProcessSetting{            //Mp3은 ProcessSetting함수를 상속받아 사용
  public Mp3(int pn, int as, int at, int pt){//ProcessSetting과 같은 인자를 씀
    super(pn,as,at,pt);                      //부모 클래스의 생성자를 호출
  }
}

//=====================Queue==============================//
class Circular_Queue {               //스케줄링에 필요한 Queue

    int rear = 0;                    //Queue의 시작 number(start number is 0)
    int front = 0;                   //Queue의 시작 number(start number is 0)
    int maxsize = 0;                 //배열의 크기(size of array)
    ProcessSetting[] circular_Queue; //배열(array)

    public Circular_Queue(int maxsize){//원형 큐
        this.maxsize = maxsize;        //배열의 크기
        circular_Queue = new ProcessSetting[this.maxsize];//새로운 배열 생성
    }

    public boolean EmptyCheck(){    //배열이 공백 상태인지 check하는 함수(check array-->empty)
        if(rear == front){          //rear와 front가 같으면(배열이 비어있으면)
            return true;            //true를 return해서 배열이 공백임을 check
        }
        return false;               //아니면 false를 return해서 배열이 공백이 아님을 check
    }

    public boolean FullCheck(){       //배열이 포화 상태인지 check하는 함수(check array-->full)
        if((rear+1)%maxsize == front){//만약 (rear+1)%maxsize == front이면
            return true;              //ture를 return해서 배열이 포화상태임을 check
        }
        return false;                 //아니면 false를 return해서 배열이 포화상태가 아님을 check
    }

    public void EnQueue(ProcessSetting p){//Queue에 data 삽입
        if(FullCheck()){                  //배열이 포화상태일 경우(if array-->full)
            System.out.println("Queue is Full!!");//Queue가 포화상태임을 알림
        }
        else{                         //배열이 포화상태가 아닐 경우(if array-->!full)
            rear = (rear+1) % maxsize;//rear pointer를 1증가시키고
            circular_Queue[rear]=p;   //그 위치에 data 삽입
        }
    }

    public void nextPoint(){  //다음 배열 가르키기

        if(EmptyCheck()){                //배열이 공백 상태일 경우(if array-->empty)
            System.out.println("empty!");//배열이 공백상태임을 알림
        }
        else  {                       //배열이 공백 상태가 아닐 경우(if array-->!empty)
            front = (front+1)%maxsize;//front pointer를 1 증가시키기
        }
    }

    public ProcessSetting DeQueue(){//그 위치에 있는 data 빼내기
      front = (front+1)%maxsize;    //front pointer를 1 증가시키고
      return circular_Queue[front]; //해당 위치에 있는 data를 배열에서 가져옴
    }
}
//======================Scheduling=========================
class Scheduling{               //스케줄링 함수

  Circular_Queue queue=new Circular_Queue(4);//data를 받아오는 Queue
  Circular_Queue finishQueue=new Circular_Queue(4);//스케줄링이 끝난 프로세스를 저장하는 Queue
  public static Mp1 p1;         //프로세스 p1
  public static Mp2 p2;         //프로세스 p2
  public static Mp3 p3;         //프로세스 p3
  public static int Time;       //스케줄링한 전체 시간(Whole Processing Time)

  //---------------------------------------------------------------------------
  public void inputProcess(){         //프로세스 입력 받기
    Scanner sc=new Scanner(System.in);//Scanner를 통해 사용자로부터 입력을 받음
    for(int i=0;i<3;i++){             //총 3개의 프로세스 존재
      System.out.print("\n");
      System.out.printf("Enter Arrival Sequence of Process(1 or 2 or 3) %d: ",i+1);
      //프로세스가 도착하는 순서 입력
      int as = sc.nextInt();
      System.out.printf("Enter Arrival Time of Process (one process must be 0) %d: ",i+1);
      //프로세스가 도착하는 시간 입력
      int at = sc.nextInt();
      System.out.printf("Enter Processing Time of Process %d: ",i+1);
      //프로세스가 실행되는 시간 입력
      int pt = sc.nextInt();
      System.out.print("\n");

      if(i==0){                //i가 0인 경우
        p1=new Mp1(1,as,at,pt);//p1 프로세스 생성
        p1.rt=p1.pt;           //초기에 p1의 실행해야하는 시간 중 남은 시간은 p1의 총 실행되는 시간과 같음
        p1.wt=0;               //초기에 p1이 대기한 시간은 0
        p1.it=0;               //초기에 p1이 실행된 시간은 0
      }
      else if(i==1){           //i가 1인 경우
        p2=new Mp2(2,as,at,pt);//p2 프로세스 생성
        p2.rt=p2.pt;           //초기에 p2의 실행해야하는 시간 중 남은 시간은 p2의 총 실행되는 시간과 같음
        p2.wt=0;               //초기에 p2가 대기한 시간은 0
        p2.it=0;               //초기에 p2가 실행된 시간은 0
      }
      else{                    //그 외에 경우
        p3=new Mp3(3,as,at,pt);//p3 프로세스 생성
        p3.rt=p3.pt;           //초기에 p3의 실행해야하는 시간 중 남은 시간은 p3의 총 실행되는 시간과 같음
        p3.wt=0;               //초기에 p3이 대기한 시간은 0
        p3.it=0;               //초기에 p3이 실행된 시간은 0
      }
    }
  }

//---------------------------------------------------------------------------
  public void inputQueue(){        //Queue에 data를 넣음
    Scheduling sd=new Scheduling();//스케줄링 실행
    sd.inputTimeslice();           //입력받은 Timeslice를 가져옴
    sd.inputProcess();             //입력받은 프로세스 정보를 가져옴

    for (int i=1;i<=3;i++){
      if(p1.as==i){       //p1 프로세스의 도착 순서가 i일 경우
        queue.EnQueue(p1);//p1을 Queue 배열 안에 삽입
      }
      else if(p2.as==i){  //p2 프로세스의 도착 순서가 i일 경우
        queue.EnQueue(p2);//p2를 Queue 배열 안에 삽입
      }
      else{ //p3.as=i     //p3 프로세스의 도착 순서가 i일 경우
        queue.EnQueue(p3);//p3를 Queue 배열 안에 삽입
      }
    }
  }

  //---------------------------------------------------------------------------
  public void startScheduling(){                           //스케줄링 시작
    System.out.println("==========Start==========");
    System.out.println("-----------------"+Time);          //현재 시간 출력

    while(queue.rear!=queue.front){                        //Queue가 비어있지 않은동안
      if(queue.circular_Queue[(queue.front+1)%4].at<=Time){//Queue[front+1]의 도착시간이 현재 시간보다 작거나 같으면
        queue.circular_Queue[(queue.front+1)%4].it += queue.circular_Queue[(queue.front+1)%4].rt;//현재 실행 시간에 남은 시간을 더한다(ing time+rt)
        if((queue.front+1)%4!=queue.rear){                 //만약 Queue[front+1]이 Queue[rear]의 위치가 같지 않으면(Queue에 data가 존재한다면)
          for(int k=2;k<=3;k++){
            if((queue.rear+1)%4 != (queue.front+k)%4){//만약 Queue[rear+1]과 Queue[front+2] 그리고 Queue[front+3]의 위치가 같지 않으면
              queue.circular_Queue[(queue.front+k)%4].wt+=queue.circular_Queue[(queue.front+1)%4].rt;
              //Queue[front+2] 그리고 Queue[front+3]의 대기 시간에 Queue[front+1]의 남은 시간을 더해준다
            }
          }
        }

        Time+=queue.circular_Queue[(queue.front+1)%4].rt;
        //전체 시간은 지난 전체 시간에 Queue[front+1]의 남은 시간을 더해준다(whole time=last whole time+rest time)

        queue.circular_Queue[(queue.front+1)%4].rt = queue.circular_Queue[(queue.front+1)%4].pt - queue.circular_Queue[(queue.front+1)%4].it;
        //Queue[front+1]의 남은 시간은 Queue[front+1]의 전체 시간에서 진행 시간만큼 뺀시간이므로 이를 계산한다(rt=pt-it)
        finishQueue.EnQueue(queue.circular_Queue[(queue.front+1)%4]);//스케줄링이 끝난 Quene는 finishQueue배열에 EnQueue한다
        queue.circular_Queue[(queue.front+1)%4].responsetime=Time;   //Queue[front+1]의 반환시간은 현재 시간 Time이므로 이를 대입
        //
        System.out.printf("1: %d %d %d\n",p1.wt,p1.it,p1.rt);//실행되는 프로세스1의 wt, it, rt순으로 출력하여 스케줄링되는 과정을 확인할 수 있다
        System.out.printf("2: %d %d %d\n",p2.wt,p2.it,p2.rt);//실행되는 프로세스2의 wt, it, rt순으로 출력하여 스케줄링되는 과정을 확인할 수 있다
        System.out.printf("3: %d %d %d\n",p3.wt,p3.it,p3.rt);//실행되는 프로세스3의 wt, it, rt순으로 출력하여 스케줄링되는 과정을 확인할 수 있다
        System.out.printf("process%d & finished!\n",queue.circular_Queue[(queue.front+1)%4].processname);//스케줄링이 끝난 프로세스를 출력
        System.out.println("-----------------"+Time);        //현재까지의 시간을 출력
        //

        queue.nextPoint();      //다음 queue로 pointer를 옮김(move to next queue)
        finishQueue.nextPoint();//finishQueue도 마찬가지로 다음 queue로 pointer를 옮김
      }
      else{                            //Queue[front+1]의 도착시간이 현재 시간보다 크면
        queue.EnQueue(queue.DeQueue());//DeQueue함수를 이용하여 Queue 배열에서 해당 data를 뺀 다음 EnQueue함수를 이용하여 Queue의 마지막 부분에 다시 넣음(dequeue-->Enqueue)
      }
    }
    //finish while
    p1.wt-=p1.at;//p1의 최종 대기 시간은 p1의 대기 시간에서 p1의 도착 시간을 뺀 값이다
    p2.wt-=p2.at;//p2의 최종 대기 시간은 p2의 대기 시간에서 p2의 도착 시간을 뺀 값이다
    p3.wt-=p3.at;//p3의 최종 대기 시간은 p3의 대기 시간에서 p3의 도착 시간을 뺀 값이다
    System.out.println("==========Finish==========\n\n");

    System.out.println("=================================================================================================================");
    System.out.printf("%-25s%-25s%-25s%-25s%-25s\n","Process name", "Arrival Sequence","Arrival Time","Processing Time","Wait Time");
    //프로세스 이름, 도착 순서, 도착 시간, 프로세싱 시간, 기다리는 시간을 출력을 통해 나타낸다
    for(int j=1;j<=3;j++){
      System.out.printf("     %-20d       %-20d   %-20d     %-20d    %-20d\n",finishQueue.circular_Queue[j].processname,finishQueue.circular_Queue[j].as,finishQueue.circular_Queue[j].at,finishQueue.circular_Queue[j].pt,finishQueue.circular_Queue[j].wt);
    }//총 3개의 프로세스의 결과를 출력
    System.out.println("=================================================================================================================");

    System.out.println("\n\n\n=============Result=============");
    System.out.println("Whole Time: "+Time);//전체 프로세스 진행 시간을 출력
    System.out.printf("Average Waiting Time: %.4f\n", ((double)p1.wt+(double)p2.wt+(double)p3.wt)/3);//전체 프로세스의 평균 대기 시간 출력
    System.out.printf("Average Response Time: %.4f\n",((double)p1.responsetime+(double)p2.responsetime+(double)p3.responsetime)/3);
    //전체 프로세스의 평균 반환시간 출력
    System.out.println("================================");
  }
}

//======================Main================================
public class Fcfs{

  public static void main(String[] args){//main함수를 통해 스케줄링을 시작

    Scheduling s=new Scheduling();//스케줄링
    s.inputQueue();               //Queue에 data를 삽입
    s.startScheduling();          //스케줄링 시작
  }
}
