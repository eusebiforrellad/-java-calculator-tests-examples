package Calc;

import static java.lang.Double.NaN;
import static java.lang.Double.isFinite;
import java.util.Random;
import javax.swing.JPanel;
public class Cal extends JPanel {
    static Thread cercaThread;
    static Thread tempsThread;
    int itext;
    static boolean stopBucle=true;
    boolean condicioDeParada=false;
    static boolean stop=false;
    public static int longi;//Cal.longi es la logitud de la taulaV = taulaV.length
    public static int longiP;//Cal.longiP=taulaP.parametres[0].length-1; nombre de fileres
    public static boolean[] longiPBol;//si la filera de taulaP-2 conte parametrevariable es true longitud=longiP
    public static double dVO[];//matriu utilitzada per la sequencia de operacions de la matriu indexs
    public static double dvo[];//matriu arraycopy de dVO
    public static String sVO[];
    public static int indexs[][];//matriu de calcul el segon[] si=0 operacio s1 1 index del numero i desti de la operacio si 2 index del numero, el calcul comença pel final de la funcio
    public static int indexsVar[][];////el segon index pot esser 0, 1, 2  s1 0 el primer index de indexVar es la posicio de la dins la matriu dVO, si 1.- es la posicio matriu Cur.matriuVar, si 2._ es el la funcio parcial relacionada amb el parametre o -1 si es variable general
    public static int indexsVarG[][];//"" nomes variables Generals el segon index nomes pot esser 0 o 1 mateix significat que indexsVar
    public static int indexsVGI[][];//sumatoris amb variables generals que es troben a la funcio general el segon index es la posicio del sumatoris, el primer es la posicio del sumatori a sVO de la funcio principal, 
    public static boolean[][] indexsInteg_VG;//indexsInteg_VG[o][i] si true vol dir que la variable general del primer index o(taula variables filera) es troba directa o indirectament mitjançant funcions parcials al menys un cop en la integral del segon index i
    public static boolean integralBol=false;//si true vol dir que a la funcio principal hi ha sumatori que conte variables generals
    public static boolean derivadaBol=false;//si true vol dir que directa o indirectament mitjançant sumatoris hi ha funcions parcials no constants que afecten la funcio; a taulaF es guarden per cada filera a taulaF.tD_VG_derivadaBol[fil]
    public static int[][] indexsParVG;//en el calcul de les funcions parcials (cada filera) aquest index permeten fer el calcul de la funcio parcial) quan no conte un parametre variable i si variable general a la funcio principal
    public static int[][] indexsParNoVar;//les fileres de taulaP que no contenent parametres variables ni variables generals directes o indirectes contindran un sol valor numeric
    public static int[][] indexsParv;//[][0] possicio a dvo; [][1] possicio a taulaV
    public static int[][] indexsParV;//mateixa longitud que taulaP.varPTV_varPTP
    public static int[][] limitindexsVarGen;//utilitzat quant es substitueix nomes una variable a dVO; per cada funcio es contrueix  la matriu Cal.limitindexsVarGen[Cal.longi(nombre de variblesGen+parametresVariables][5] la primera filera[][0] indica si 2 es un parametreVariable o -6><1 variableGeneral amb diferents condicions
    public static int indexsVGD[][];//derivades que es troben a la funcio general el segon index es la posicio de la funcio parcial, el primer es la posicio de la funcio parcial a sVO de la funcio principal, 
    int contador;
    public static double [][] dMatriuVar;
    public static int [] idxVarGen;//index del les variables Generals  de taulaV mateix ordre que taula
    public static int [] idxParVar;//index dels parametres variables primer ordenats per llargaria de la matriuVar despres de taulaV mateix ordre que taula
    public static double resultat; //resultat de la funcio global
    double iantRes=NaN;//,antR=NaN;
    public static double[] resultatM;//matriu de resultats de les funcions parcials
    public static double[] resultatP;//matriu de resultats de les funcions parcials longitud igual idxParVar (nombre de parametres Variables)
    public static double[] resultatMcop;// copia matriu de resultats de les funcions parcials
    static long[] segCero;
    static long segment;
    static double [] varAct;
    static double [] varCopia;
    public static int escalaLog=0;
    static boolean escalaLinial=false;
    static int[] limSupCalc;
    static int limSCalc;
    static long[] minIncrSignif;
    static double[] pondIncrVar;
    public static long[] cursor;//eslong i sempre positiu associat a les abcisees (variables) que poden esser + o -  el seu valor maxim es segment
    public static long[] cursorMinim;
    int idxP;//index de la variable amb maxim pendent (es corretgeig quan es mou el cursor  i tambe quan el cursor supera els limits min maxSlider)
    static boolean[] varPocSignif;
    public static boolean[] vargOparv;//si l'index es true es una variable general si es false es un parametre variable
    public static int[] idxresultatM;//matriu mateixa longitud que longi la filera del parametre es coloca en la possicio de la matriu aixi si hi ha un parametre variable a la filera tres i aquesta es el primer parametre va la filera 3 hi posara el cero(encara que les possicions de les variables generals no es fan servir)
    static suportID supID;
    public static boolean[][] hihaFPIntegrBol;//boolean de [sumat.length+1][simbols.length+1] que relaciona sumatoris qur contenent funcionsParcials no constants
    public static double posic(int i,long m){//entra possicio  (long)cursor o long  i retorna valor abcisa 
        if(!escalaLinial){
            if(dMatriuVar[i][0]>0) return Math.pow(10,dMatriuVar[i][3]+dMatriuVar[i][5]*m/segment);
            if(dMatriuVar[i][1]<0) return -Math.pow(10,dMatriuVar[i][3]-dMatriuVar[i][5]*m/segment);
            else{
                if(m>=segCero[i]) return Math.pow(10,dMatriuVar[i][4]+dMatriuVar[i][6]*(m-segCero[i])/(segment-segCero[i]));
                else return -Math.pow(10,dMatriuVar[i][3]-dMatriuVar[i][5]*(m)/(segCero[i]));
            }
        }
        else return dMatriuVar[i][0]+dMatriuVar[i][2]*m/segment;
    }
    public static void escalaNoLinial(int li,int ls){//double min=2.4703282292062327209E-324;
        for(int i=li;i<ls;i++){
            if(dMatriuVar[i][0]==0){//si el limit inferior (dMatriuVar[i][0]) es cero (interval tot positiu) es transforma en 10^-16 o 10^-(16+log(limitsuperior(si<0)))
                if(dMatriuVar[i][1]<1)dMatriuVar[i][3]=Math.log10(dMatriuVar[i][1])+taulaC.ceroLog;
                else dMatriuVar[i][3]=taulaC.ceroLog;
                dMatriuVar[i][0]=Math.pow(10,dMatriuVar[i][3]);
            }//si no es cero introdueix el logaritme del valor absolut  a dMatriuVar[i][3]
            else{dMatriuVar[i][3]=Math.log10(Math.abs(dMatriuVar[i][0]));}//(el interval pot esser positu negatiu o ambdos)si el limit inferior no es cero es pren log del valor absolut
            if(dMatriuVar[i][1]==0){//(interval tot negatiu similar al cas en que el interval es positiu per en
                if(dMatriuVar[i][0]>-1)dMatriuVar[i][4]=Math.log10(-dMatriuVar[i][0])+taulaC.ceroLog;
                else dMatriuVar[i][4]=taulaC.ceroLog;
                dMatriuVar[i][1]=-Math.pow(10,dMatriuVar[i][4]);
            }
            else{dMatriuVar[i][4]=Math.log10(Math.abs(dMatriuVar[i][1]));} 
            if(dMatriuVar[i][0]<0&&dMatriuVar[i][1]>0){
                double mi=dMatriuVar[i][3];//mi es el menor valor del logaritmes mes propers a cero tant dMatriuVar[i][3] com dMatriuVar[i][4] son els logaritmes del valor absolut
                if(mi>dMatriuVar[i][4])mi=dMatriuVar[i][4];
                if(mi>=0)mi=taulaC.ceroLog;//si el minim valor te un exponent positiu(log(var)>0 el cero sera 1E-16
                else mi=mi+taulaC.ceroLog;//si el minim valor te un exponent negatiu(log(var)<0 el cero sera 1E-16+expnent negatiu
                if(mi<-323.3062153431158)mi=-323.3062153431158;//double dou=0.2470999999999999E-323; log=-323,3062153431158
                double num=-mi+dMatriuVar[i][3];//si [3]=-8, [4]=-3, mi=-24, num=16 || si [3]=2; [4]=-10, min=-26, num=28
                segCero[i]=Math.round(Cal.segment*num/(-2*mi+dMatriuVar[i][4]+dMatriuVar[i][3]));//si min=-24, num=16 segCero=segment*16/37 || si min=-26, num=28, segCero=segment*28/44
                dMatriuVar[i][6]=-mi+dMatriuVar[i][4];//indica el recorregut del exponent de la part positiva
                dMatriuVar[i][4]=mi;
                dMatriuVar[i][5]=num;//indica el recorregut del exponent de la part negativa
            }
            else {//la diferencia sempre sera positiva
                if(dMatriuVar[i][0]>0)dMatriuVar[i][5]=dMatriuVar[i][4]-dMatriuVar[i][3];
                else dMatriuVar[i][5]=-dMatriuVar[i][4]+dMatriuVar[i][3];
            }
        }
    }
    String text(int i){
        switch(i){
            case 0: return "funci"+Func.rB.getString("o_")+":"+resultat+splitPan.FIL;
            case 1: return "(cerca alternativa), funci"+Func.rB.getString("o_")+":"+resultat+splitPan.FIL;
        }
       return "";
    }
    public void continuaThread(){
        Thread continua = new Thread(new Threads3()); continua.setPriority(Thread.MAX_PRIORITY);continua.start();
    }
    class Threads3 implements Runnable {
        public void run() {
            String [] var=new String[longi];
            String [][] vars=new String[1][longi];//guarda tots els conjunts de variables que donen un minim relatiu
            String [][] varsCop=new String[1][longi];
            for(int i=0;i<longi;i++)vars[0][i]=splitPan.rodo(posic(i,cursorMinim[i]));
            System.arraycopy(vars[0],0,var,0,longi);
            double res=resultat;
            long[] cur=new long[longi];
            System.arraycopy(cursorMinim,0,cur,0,longi);
            if(!isFinite(res))res=Cur.maxim;
            boolean escalaLinealOriginal=escalaLinial;
            boolean escala=escalaLinial;
            stop=false;
            Random ran = new Random(System.currentTimeMillis());
            int cont=2;
            varPocSignif=new boolean[longi];
            
            while(!stop){
                escalaLinial=!escalaLinial;
                if(cont==-2)for(int i=0;i<longi;i++)cursorMinim[i]=segment;
                else if(cont==-1)for(int i=0;i<longi;i++)cursorMinim[i]=0;
                else if(cont==0){double d=ran.nextDouble();for(int i=0;i<longi;i++)cursorMinim[i]=(long)(segment*d);}
                else for(int i=0;i<longi;i++)cursorMinim[i]=(long)(segment*ran.nextDouble());
                cont++;if(cont>1)cont=0;
                cercaThread(true);
                while(!stopBucle){try{Thread.sleep(250L);} catch( InterruptedException e) {}}
                while(tempsThread.isAlive()){try{Thread.sleep(100L);} catch( InterruptedException e) {}}
                if(isFinite(resultat)){
                    if(Func.ampliarInfo||res>=resultat){
                        for(int i=0;i<longi;i++)var[i]=splitPan.rodo(posic(i,cursorMinim[i]));
                        boolean b=false;
                           for(int j=0;j<vars.length;j++){
                               b=false;
                               for(int i=0;i<longi;i++){
                                   if(!var[i].equals(vars[j][i])){b=true;i=longi;}//hi ha almenys una diferencia en les variables  per tant aquest grup de variables no es guarda a vars
                               }
                               if(!b)j=vars.length;//si b es false aquest grup de variables filera j coincideixen amb el grup actual i per tan finalitza i no guardara el nou grup sino hi ha alguna diferncia i continual amb la seguent fileracontinua
                           }
                       if(res>resultat||b){ 
                            varsCop=new String[vars.length+1][longi];
                            for(int j=0;j<vars.length;j++)System.arraycopy(vars[j],0,varsCop[j],0,longi);
                            System.arraycopy(var,0,varsCop[vars.length],0,longi);
                            vars=new String[varsCop.length][longi];
                            for(int j=0;j<vars.length;j++) System.arraycopy(varsCop[j],0,vars[j],0,longi);
                            if(res>resultat){
                                escala=escalaLinial;
                                res=resultat;
                                System.arraycopy(cursorMinim,0,cur,0,longi);
                                if(!Func.capturaManualBol){int ip=Cur.indexPunts;if(ip>5)Cur.indexPunts=5;Cur.captura();Cur.indexPunts=ip;}
                            }
                            presRes();
                       }
                   }
                }
            }
            stopBucle=false;
            if(res!=Cur.maxim){
                resultat=res;
                System.arraycopy(cur,0,cursorMinim,0,longi);
            }
            else{
                escalaLinial=escalaLinealOriginal;
                Func.falseTrue();
                stop=true;
                return;
            }
            escalaLinial=escala;
            presRes();
            if(!Func.capturaManualBol)Cur.captura();
            escalaLinial=escalaLinealOriginal;
            Func.falseTrue();
            stop=true;
        }
    }
    public void cercaThread(boolean b){
        if (!b){
            varPocSignif=new boolean[longi];
            for(int i=0;i<longi;i++) minIncrSignif[i]=(long)Math.pow(2,28);
        }
        stopBucle=false;
        resultat=Cur.maxim;
        tempsThread = new Thread(new ThreadTemps()); tempsThread.setPriority(Thread.MIN_PRIORITY);tempsThread.start();
        cercaThread = new Thread(new ThreadCerca()); cercaThread.setPriority(Thread.MAX_PRIORITY);cercaThread.start();
    }
    public class ThreadTemps implements Runnable{
        public void run() {
            temps();
            if(stop){
                stopBucle=false;
                if(!Func.capturaManualBol)Cur.captura();
                Func.contBol=true;
                Func.falseTrue();
            }
        }
    }
    public void temps(){
        contador=0;
        double var[]=new double[longi];
        double antVar[]=new double[longi];
        long cur[]=new long[longi];
        double antRes=Cur.maxim;
        int dv=-taulaC.digitsValids-6;if(dv<-15)dv=-15;if(dv>-8)dv=-8;
        int con=0,conta=3;
        double antR=Cur.maxim;
        while(!stopBucle){
            con++;
            boolean digitsBol=false;
            //if(cur.length!=longi)return;
            System.arraycopy(cursorMinim,0,cur,0,longi);
            for(int i=0;i<longi;i++)var[i]=posic(i, cur[i]);
            boolean b=true;
            if(conta<contador&&antRes!=Cur.maxim){
                double d=0;
                int cont=0;
                for(int i=0;i<longi;i++){
                    double d0=Math.abs(antVar[i])+Math.abs(var[i]);
                    if(d0>0)d0=Math.log10(Math.abs(var[i]-antVar[i])/d0);
                    if(isFinite(d0)){d+=d0;cont++;}
                    else b=false;
                }
                if(b){
                    if(d/cont<-taulaC.digitsValids)digitsBol=true;
                    d=(Math.abs(resultat)+Math.abs(antRes));//*(contador-conta);
                    if(d>0)d=Math.log10(Math.abs(resultat-antRes)/d);
                    conta=contador+3;
                    System.arraycopy(var,0,antVar,0,Cal.longi);
                    if(d<=dv&&digitsBol){condicioDeParada=true;}
                    if(antR!=resultat){
                        if(con>=12&&!taulaI.hihaDades||con>=24){
                            if(!Func.capturaManualBol){int ip=Cur.indexPunts;if(ip>5)Cur.indexPunts=5;Cur.captura();Cur.indexPunts=ip;}
                            Func.append(0,"m"+Func.rB.getString("i_")+"nim: "+text(itext));con=0;antR=resultat;
                        }
                    }
                }
            }
            if(b)antRes=resultat;
            //else {Func.append(0,"stop (resultat parcial indefinit)"+splitPan.FIL);condicioDeParada=true;}
        
            try{Thread.sleep(100L);} catch( InterruptedException e) {}
        }
        if(taulaI.hihaDades){
            try{if(supID.integralThread!=null){while(supID.integralThread.isAlive()){Thread.sleep(50L);}}}
            catch(Exception e){}
        }
        if(!Func.tipusFuncioBol){
            while(cercaThread.isAlive()){try{Thread.sleep(50L);} catch( InterruptedException e) {}}
        }
    }
    class ThreadCerca implements Runnable {public void run() {cerca();}}
    public void cerca(){
        condicioDeParada=false;
        splitPan.temp0();
        for(int i=0;i<longi;i++)limSupCalc[i]=6;
        Cur.capturaC.setEnabled(true);
        iantRes=Cur.maxim;
        idxP=-1;
        itext=0;
        int con=0;
        while(!stopBucle){
            if(condicioDeParada){stopBucle=true;presRes();return;}
            contador++;
            long[] d=new long[longi];
            System.arraycopy(cursorMinim, 0, d,0,longi);
            calcMinPendentVar(false,cursor,cursorMinim,varAct,resultat,dvo,dVO,sVO,limSupCalc,minIncrSignif,resultatP,resultatMcop);
            if(taulaP.hihaParVar)calcMinPendentPar();//if(taulaP.hihaParVar)calcMinPendentPar(dvo,dVO,sVO,varAct,cursor,cursorMinim,resultatP,limSupCalc,resultatM,resultatMcop,minIncrSignif,resultat);
            if(longi>1){
                long max=0;
                for(int j=0;j<longi;j++){
                    d[j]=cursorMinim[j]-d[j];//diferencia entre el cursorMinim actual i l'anterior
                    long i=Math.abs(d[j]);
                    if(max<i){max=i;}
                }
                if(max==0)for(int j=0;j<longi;j++)pondIncrVar[j]=1;
                else for(int j=0;j<longi;j++)pondIncrVar[j]=(double)d[j]/(double)max;
                idxP+=1;if (idxP>idxVarGen.length-1)idxP=0;
                resultat=calcMinPendent(idxP,limSCalc,cursor,cursorMinim,pondIncrVar,dvo,dVO,sVO,varAct,resultat);
            }
            if(!isFinite(resultat)){
                stopBucle=true;
                if(stop)presRes();
                return;
            }
            if(resultat==iantRes){
                if(con==0){testIncr(1E15);}
                else if(con==1){testIncr(2E15);}
                else if(con==2){testIncr(4E15);}
                else if(con==3){testIncr(8E15);}
                else if(con>3){stopBucle=true;}
                con++;
            }
            else if(con>2)con=2;
            iantRes=resultat;
        }
        if(stop)presRes();
    }
   void presRes(){
       if(!Func.tipusFuncioBol){
            if(Func.ampliarInfo)Func.append(0,"temps par.: "+splitPan.temp()+" seg., ");
            Func.append(0,"temps tot.: "+splitPan.tempt()+" seg., "+splitPan.FIL);
            Func.append(2,"m"+Func.rB.getString("i_")+"nim: "+"funci"+Func.rB.getString("o_")+": ");
            if(isFinite(resultat))Func.append(2,splitPan.rodo(resultat));
            else Func.append(2,""+resultat);
            if(longi>1) Func.append(2,splitPan.FIL);
            Func.append(2,Cur.presentaRes(cursorMinim, true)+splitPan.FIL+splitPan.FIL);
       }
       else{
           if(taulaF.sfreqPres[taulaF.fila].contains(";")||taulaF.sfreqPres[taulaF.fila].contains("n")){
                if(Func.ampliarInfo){
                    Func.append(0,"temps par.: "+splitPan.temp()+" seg., ");
                    Func.append(0,"temps tot.: "+splitPan.tempt()+" seg., "+splitPan.FIL);
                }
                Func.append(2,taulaF.simbolsT[taulaF.fila]+" (m"+Func.rB.getString("i_")+"nim: "+"funci"+Func.rB.getString("o_")+"): ");
                if(isFinite(resultat))Func.append(2,splitPan.rodo(resultat));
                else Func.append(2,""+resultat);
                if(longi>1) Func.append(2,splitPan.FIL);
                Func.append(2,Cur.presentaRes(cursorMinim, true)+splitPan.FIL);
           }
       }
    }

    public static double calcul(double[] dvo){
        if(indexs.length==0)return dvo[0];
        for(int i=0;i<indexs.length;i++)dvo[indexs[i][1]]=caseOpFun(indexs[i][0],dvo[indexs[i][1]],dvo[indexs[i][2]]);
        double d=dvo[indexs[indexs.length-1][1]];
        return d;
    }
    void testIncr(double min){//primer calcula les variables actualitza dVO i calculcula el resultat de la funcio global 
        for(int i=0;i<longi;i++)varAct[i]=posic(i,cursorMinim[i]);
        if(indexsParV.length>0){resultatP=new double[idxParVar.length];resultat=resultatMatrP(true,dvo,dVO,sVO,varAct,resultatP,resultatM,cursor,cursorMinim);}
        else {actualitzadVO(dvo, dVO,sVO,varAct,cursor,cursorMinim);resultat= calcul(dvo);}//resultat=resultatdVO(dvo, dVO,sVO,varAct,cursor,cursorMinim,idxAmp);}
        if(!isFinite(resultat)){//si el resultat no es valid modifica els cursors i realitza un segon calcul prop de l'anterior
            for(int i=0;i<longi;i++){
                if(minIncrSignif[i]<1)minIncrSignif[i]=1;
                cursorMinim[i]+=minIncrSignif[i];
                if(cursorMinim[i]>segment)cursorMinim[i]-=2*minIncrSignif[i];
                varAct[i]=posic(i,cursorMinim[i]);
            }
            if(indexsParV.length>0)if(indexsParV.length>0){resultatP=new double[idxParVar.length];resultat=resultatMatrP(true,dvo,dVO,sVO,varAct,resultatP,resultatM,cursor,cursorMinim);}
            else {actualitzadVO(dvo, dVO,sVO,varAct,cursor,cursorMinim);resultat= calcul(dvo);}
        }
        boolean b1=false;
        String s="";
        for(int i=0;i<longi;i++){
            if (testIncr(i,min,cursor,cursorMinim,varAct,minIncrSignif,resultat, resultatP,limSupCalc,dvo,dVO,sVO,resultatMcop)&&!varPocSignif[i]){
                b1=true;
                s+=taulaV.varstaulaV[i]+"("+varAct[i]+")  ";
                varPocSignif[i]=true;
            }
        }
        if(b1&&Func.ampliarInfo)Func.append(0,"funcio constant en un ampli interval de valors de les variables, centrades en el valor: "+s+splitPan.FIL);
        limSCalc=0;for(int i=0;i<longi;i++) if(limSCalc<limSupCalc[i])limSCalc=limSupCalc[i];
        //System.out.println(min);for(int i=0;i<longi;i++)System.out.print(limSupCalc[i]+" ");System.out.println();
        
    }
    //void calcMinPendentPar(double[] dvo,double[] dVO,String[] sVO,double[] varAct,long[] cursor,long[] cursorMinim,double[] resultatP,int[] limSupCalc,double[] resultatM,double[] resultatMcop,long[] minIncrSignif,double resultat){
    void calcMinPendentPar(){
        resultatP=new double[idxParVar.length];resultatMatrP(false,dvo,dVO,sVO,varAct,resultatP,resultatM,cursor,cursorMinim);
        int[] per={1,-1};
        for(int o=0;o<idxParVar.length;o++){
            int i=idxParVar[o];
            long am=segment;
            for(int n=0;n<limSupCalc[i];n++){
                int l=2;
                per[0]=1;
                am/=2;
                if(cursorMinim[i]+am>segment) {per[0]=-1;l=1;}
                else if(cursorMinim[i]-am<0) {l=1;}
                for(int k=0;k<l;k++){
                    cursor[i]=cursorMinim[i]+(long)(per[k]*am);
                    varAct[i]=posic(i,cursor[i]);
                    double res=resultat1P(i,dvo,dVO,sVO,varAct,resultatMcop);
                    if(resultatP[o]>=res){
                        if(resultatP[o]>res){
                            resultatP[o]=res;
                            cursorMinim[i]=cursor[i];
                            for (int[] m : indexsParV) {
                                if (m[0] == i)for (int ix = 0; ix < m[6]; ix++) {
                                    int j = m[ix+7];
                                    if(sVO[j].startsWith("-"))dVO[j]=-varAct[i];
                                    else dVO[j]=varAct[i];
                                }
                                resultatM[m[1]] = resultatMcop[m[1]];
                            }
                        }
                        else {
                            if(n<limSupCalc[i])n=limSupCalc[i];//si son iguals realitza un calcul mes
                        }//si es el mateix valor retorna hipotesi de funcio cte en l'interval
                    }
                }
            }
        }
        double sum=0;
        for(int i=0;i<resultatM.length;i++)sum+=resultatM[i];
        if(resultat>sum)resultat=sum;
    }
    public static void calcMinPendentVar(boolean varFix,long[] cursor,long[] cursorMinim,double[] varAct,double resultat,double[] dvo,double[] dVO,String[] sVO,int[] limSupCalc,long[] minIncrSignif,double[] resultatP,double[] resultatMcop){
        int[] per={1,-1};
        int lim=1;
        for(int o=0;o<idxVarGen.length;o++){
            int i=idxVarGen[o];
            if(varFix&&Cur.variableFixaBol[i]){//nomes s'utilitza quant be de Cur
                Cur.varAct[i]=Cur.variableFixa[i];
                actualitzadvo(i,dvo,dVO,sVO,varAct,cursor);
                System.arraycopy(dvo, 0, dVO, 0, dVO.length);
            }
            else{
                long am=Cal.segment;
                for(int idx=lim;idx<limSupCalc[i];idx++){
                    int l=2;
                    per[0]=1;
                    am/=2;
                    if(cursorMinim[i]+am>segment) {per[0]=-1;l=1;}
                    else if(cursorMinim[i]-am<0) {l=1;}
                    for(int k=0;k<l;k++){
                        cursor[i]=cursorMinim[i]+(long)(per[k]*am);
                        varAct[i]=posic(i,cursor[i]);
                        actualitzadvo(i,dvo,dVO,sVO,varAct,cursor);double res=calcul(dvo);//en principi el calcul es fa i dVO no esta actualitzat aixo implica que els valor de la resta de variables es mante igual que despres del calcul de parametres
                        if(resultat>=res){
                            if(resultat>res){
                                resultat=res;
                                cursorMinim[i]=cursor[i];
                            }
                            else  idx=limSupCalc[i];//si son iguals finalitza
                        }
                    }
                }
            }
            varAct[i]=Cal.posic(i,cursorMinim[i]);
            actualitzadvo(i,dvo,dVO,sVO,varAct,cursor);
            System.arraycopy(dvo, 0, dVO, 0, dVO.length);
        }
    }
    static double resultatMatrP(boolean actualdVO,double[] dvo,double[] dVO,String[] sVO,double[] varAct,double[] resultatP,double[] resultatM,long[] cursor,long[] cursorMinim){
        if (actualdVO){actualitzadVO(dvo, dVO,sVO,varAct,cursor,cursorMinim);}//si true totes les variables a dVO s'actualitzen incloses les variables integrals i derivades
        double suma=0;//utilitzar ho si cal que cur i cal continguin el prcediment independent//resultatP=new double[idxParVar.length];//matriu associada als parametres variables; resultatM va associat a les fileres de parametres
        //System.arraycopy(dVO, 0, dvo, 0, dvo.length);//fa copia a dvo que es modificara amb el calcul
        if (!actualdVO)for (int[] i : indexsParv) {
            if (sVO[i[0]].startsWith("-")) dvo[i[0]] = -varAct[i[1]];else dvo[i[0]] = varAct[i[1]];
        }   
        for (int[] j : indexsParV) {
            for (int i=j[2]; i<=j[3];i++) {dvo[indexs[i][1]]=caseOpFun(indexs[i][0],dvo[indexs[i][1]],dvo[indexs[i][2]]);}
            resultatM[j[1]] = dvo[indexs[j[3]][1]]; //indexsParV[m][1]= es la filera taulaP
            resultatP[idxresultatM[j[0]]] += resultatM[j[1]]; //indexsParV[m][0] es l'index del parametre variable
        }
        for (int[] j : indexsParVG){//quant hi ha fileres de la taulaP que no contenet parametres variables cal calcular les fileres que falten mitjançant les variables generals
            for (int i=j[2];i<=j[3];i++) {dvo[indexs[i][1]]=caseOpFun(indexs[i][0],dvo[indexs[i][1]],dvo[indexs[i][2]]);}
            resultatM[j[1]] = dvo[indexs[j[3]][1]];
        }
        for (int[] i: indexsParNoVar) resultatM[i[0]] = dvo[i[1]];
        for(int m=0;m<resultatM.length;m++)suma+=resultatM[m];
        return suma;
    }
    public static double calcMinPendent(int idxP,int limSCalc,long[] cursor,long[] cursorMinim,double[] pondIncrVar,double[] dvo,double[] dVO,String[] sVO,double[] varAct,double resultat){
        long am=segment;
        int[] per={1,-1};
        for(int idxA=0;idxA<limSCalc;idxA++){
            int l=2;
            per[0]=1;
            am/=2;
            cursor[idxP]=cursorMinim[idxP]+am;
            if(cursor[idxP]>segment) {per[0]=-1;l=1;}
            else if(cursor[idxP]<0) {l=1;}
            for(int k=0;k<l;k++){
                for(int j=0;j<longi;j++){
                    cursor[j]=cursorMinim[j]+(long)(per[k]*am*pondIncrVar[j]);//no utilitzat if(b){1+2+3+4+5+6=21=6*7/2 en el meu cas double d=Math.abs(per[k]*am-1);if(d>0)d=d*per[k]*am/2;cursor[j]+=cursorMinim[j]+(long)(d*pondIncrVar[1][j]);}//if(cursor[j]<0||cursor[j]>segment){cursor[j]=cursorMinim[j];}//b=false;j=longi;i=-999999999;}
                    if(cursor[j]<0){cursor[j]=0;}else if(cursor[j]>segment){cursor[j]=segment;}
                    varAct[j]=posic(j,cursor[j]);
                }
                actualitzadvo(dvo,dVO,sVO,varAct,cursor);double res= calcul(dvo);
                if(resultat>=res){
                    if(resultat>res){
                        resultat=res;
                        System.arraycopy(cursor,0,cursorMinim,0,longi);
                    }
                    else if(resultat==res) {idxA=limSCalc;k=l;}
                }
            }
        }
       return resultat;
    }
    //atencio si les seguents fileres mo hi son va mes rapid encara que no sembla tenir sentit
        //for(int j=0;j<longi;j++){varAct[j]=posic(j,cursorMinim[j]);}actualitzadVO();
        //nomes s'actualitzen les variables genrals les variables parametres tene el mateix valor que el anterior ajust de parametres
        //s'actualitzaran despres del primer calcul (calcMinPendentVar)
        //aixi per exemple les seguents fileres actualitzen nomes variables generals i el resultat es similar a si no i son
    static double resultat1P(int ix,double[] dvo,double[] dVO,String[] sVO,double[] varAct,double[] resultatMcop){
        double tot=0;
        for (int[] k : indexsParV)if (k[0] == ix) {
            System.arraycopy(dVO, k[4], dvo, k[4], k[5]);
            for (int i = 0; i < k[6]; i++) {
                int j = k[i+7];
                if(sVO[j].startsWith("-"))dvo[j]=-varAct[ix];
                else dvo[j]=varAct[ix];
            }
            for (int i = k[2]; i <= k[3]; i++) {
                dvo[indexs[i][1]]=caseOpFun(indexs[i][0],dvo[indexs[i][1]],dvo[indexs[i][2]]);
            }
            resultatMcop[k[1]] = dvo[indexs[k[3]][1]];
            tot += resultatMcop[k[1]];
        }
        return tot;//tot es resultat parcial de resultatP
    }
    //els calculs es fan sobre la matriu dvo i els resultats s'escriuen sobre aquesta matriu, despres del calcul dvo no conte els valors adequats per un calcul posterior
    //el proces que es segueix es el seguen: despres d'un resultat que millora l' anterior calcular les variables del nou minim a partir dels cursors i guardarles a dVO
    //per aixou utilitzar els procediments actualitzadVO() i actualitzadVO(o)
    //previ a un nou calcul fer copia de dVO a dvo guardar els nous valors de les variables que es probaran, a dvo i realizar el calcul
    //en la cerca del interval adequat minIncrSignif es dona el cas que les proves amb difernts intervals parteixen sempre de les mateixes variables inicials
    //en aques cas es mes normal fer copia inicial de dVO a dvo i fer el canvi de la variable a dvo (aquest valors no es guardaran)
    static void actualitzadVO(double[] dvo, double[] dVO,String[] sVO,double[] varAct,long[] cursor,long[] cursorMinim){//actualitza les variables a dVO i a dvo
       System.arraycopy(cursorMinim, 0, cursor, 0, cursor.length);
       actualitzadvo(dvo,dVO,sVO,varAct,cursor);
       System.arraycopy(dvo, 0, dVO, 0, dVO.length);
    }
    public static void actualitzadvo(double[] dvo,double[] dVO,String[] sVO,double[] varAct,long[] cursor){//copia dVO a dvo i actualitza les variables a dvo
        System.arraycopy(dVO, 0, dvo, 0, dvo.length);
        for (int[] i : indexsVar) {
            if (sVO[i[0]].startsWith("-")) dvo[i[0]] = -varAct[i[1]];else dvo[i[0]] = varAct[i[1]];
        }
         if(derivadaBol){
            if(!Func.tipusFuncioBol||Func.tipusFuncioBol&&taulaF.varGenDerBol[taulaF.fila][0]){
                System.arraycopy(varAct, 0, varCopia, 0, varAct.length);
                double[] d=supID.calculDerivada(varCopia,cursor);
                for (int[] i : indexsVGD) {
                    if (sVO[i[0]].startsWith("-")) dvo[i[0]] = -d[i[1]];else  dvo[i[0]] = d[i[1]];
                }
            }
        }
        if(integralBol){//si hi ha sumatoris a la funcio principal que contenen variables generals: substitueix totes les variables generals a totes les funcions integrals calcula les integrals i les adjunta a la funcio principal
            if(hihaFPIntegrBol[0][0])supID.FPIntegral();
            supID.substitueixVG(varAct);//substitueix totes les variables generals a totes les integrals(funcions i intervals) no a l funcio principal general (Cur.indexVGI)
            for(int i=0;i<taulaI.sumat.length;i++)if(suportID.hihaVGioFPaI[i]){
                if(!Func.tipusFuncioBol||Func.tipusFuncioBol&&taulaF.varGenSumBol[taulaF.fila][i+1]){
                    supID.bucle(i);
                }
            }
            for (int[] i : indexsVGI) {//substitueix a la funcio principal el valor de tors els sumatoris amb variables generals actualitzats
                if (sVO[i[0]].startsWith("-")) dvo[i[0]] = -supID.integral[i[1]];else dvo[i[0]] = supID.integral[i[1]];
            }
        }
    }
    public static void actualitzadvo(int o,double[] dvo,double[] dVO,String[] sVO,double[] varAct,long[] cursor){
       System.arraycopy(dVO, 0, dvo, 0, dvo.length);
       int ix=limitindexsVarGen[o][0];
       if(ix<-2){
            ix+=4;
            System.arraycopy(varAct, 0, varCopia, 0, varAct.length);
            double[] d=supID.calculDerivada(o,varCopia,cursor);
           for (int[] i : indexsVGD) {
               if (taulaD.VGaFuncBol[i[1]][o]) {//[0]:posicio a dVO [1]:filera de taula variables-1
                   if (sVO[i[0]].startsWith("-")) dvo[i[0]] = -d[i[1]]; else dvo[i[0]] = d[i[1]];
               }
           }
       }
        if(ix<=-1){//inclou els valors -1 i -2 en que hi ha integrals amb variables generals a la funcio integral
            if(hihaFPIntegrBol[0][0])supID.FPIntegral();
            for(int i=limitindexsVarGen[o][3];i<limitindexsVarGen[o][4];i++){
            if(suportID.sVOI[suportID.indexsVGaI[i][0]].startsWith("-"))supID.dVOI[suportID.indexsVGaI[i][0]]=-varAct[suportID.indexsVGaI[i][1]];
            else supID.dVOI[suportID.indexsVGaI[i][0]]=varAct[suportID.indexsVGaI[i][1]];
            }
            for(int i=0;i<indexsInteg_VG[o].length;i++){
                if(indexsInteg_VG[o][i]){
                    if(!Func.tipusFuncioBol||Func.tipusFuncioBol&&taulaF.varGenSumBol[taulaF.fila][i+1]){
                        supID.bucle(i);
                        for (int[] j : indexsVGI) {
                            if (j[1] == i) {//i sera la posicio del sumatoris
                                if (sVO[j[0]].startsWith("-"))dvo[j[0]] = -supID.integral[i];else dvo[j[0]] = supID.integral[i];
                            }
                        }
                    }
                }
            }
            ix+=2;
        }
        if(ix==1){//hi ha variable general >-1 a funcio principal i possible integra
            for(int i=limitindexsVarGen[o][1];i<=limitindexsVarGen[o][2];i++){
                if(sVO[indexsVarG[i][0]].startsWith("-"))dvo[indexsVarG[i][0]]=-varAct[indexsVarG[i][1]];
                    else dvo[indexsVarG[i][0]]=varAct[indexsVarG[i][1]];
            }
        }
    }
    static boolean testIncr(int j,double min,long[] cursor,long[] cursorMinim,double[] varAct,long [] minIncrSignif, double resultat, double[] resultatP,int[] limSupCalc,double[] dvo,double[] dVO,String[] sVO,double[] resultatMcop){//j rep la possicio de la variable a taulaV
        boolean bucle=true,bol=true,incrbol=false,signif=false;
        while(bucle){
            cursor[j]=cursorMinim[j]+minIncrSignif[j];
            if(cursor[j]>segment)cursor[j]=cursorMinim[j]-minIncrSignif[j];
            varAct[j]=posic(j,cursor[j]);
            double m,m1;
            if(!vargOparv[j]){m=resultat1P(j,dvo,dVO,sVO,varAct,resultatMcop); m1=resultatP[idxresultatM[j]]-m;}
            else{actualitzadvo(j,dvo,dVO,sVO,varAct,cursor);m=calcul(dvo);m1=resultat-m;}
            double p=Math.abs(m/m1);
            if(bol){
                bol=false;
                if(m1==0||p>min) {incrbol=true;}//si bo es true vol dir que l'increment de la funcio es masa petit(denominador) i cal incrementar l'interval
            }
            if(incrbol){
                if(minIncrSignif[j]>segment/4){
                    minIncrSignif[j]=segment/4;bucle=false;signif=true;
                }
                else{
                    if(m1==0||p>min)minIncrSignif[j]*=2;
                    else bucle=false;
                }
            }
            else{
                if(minIncrSignif[j]<=2){minIncrSignif[j]=2;bucle=false;}
                else{
                    if(m1!=0&&p<=min)minIncrSignif[j]/=2;
                    else{minIncrSignif[j]*=2;bucle=false;}
                }
            }
        }
        limSupCalc[j]=62-(int)(Math.log10(minIncrSignif[j])/Math.log10(2));//si minIncrSignif[j] =1 limSupCalc[j]=62; si minIncrSignif[j] =segment limSupCalc[j]=0;limSup Calc variara entre 2 i 61
        return signif;
    }
     //per introduir mes funcions modificar a Cal simbOpFun(int i) i  'caseOpFun(int i)'  ha Cur 'caseOpFun(int i)', a Func funcions i calculFun() a splitPan  tambe els errors a Integral
    public static double caseOpFun(int i,double d1,double d2){
        switch(i){
            case -5: return -d1;
            case -4: return d1+d2;
            case -3: {
                if (d2 != 0.0D) {
                    if(d1!=0.0D)return d1/d2;
                    else return 0.0D;
                }
                else return NaN; 
            }
            case -2:return d1*d2;
            case -1: {return Math.pow(d1,d2);}
            case 1:  return Math.log10(d2);
            case 2:  return Math.log(d2);
            case 3:  return Math.sin(d2);
            case 4:  return Math.asin(d2);
            case 5:  return Math.cos(d2);
            case 6:  return Math.acos(d2);
            case 7:  return Math.tan(d2);
            case 8:  return Math.atan(d2);
            case 9:  return Math.abs(d2);
            case 10:  return Math.toRadians(d2);
            case 11:  return Math.toDegrees(d2);
            case 12:  return (double) Math.round(d2);
            case 13:  { long lo = (long)d2; return (double) lo; }
            case 14:  { long lo = (long)d2; return d2 - lo;}
            case 15:  return Math.sinh(d2);
            case 16:  return Math.cosh(d2);
            case 17:  return Math.tanh(d2);
            case 18:  return factorial(d2);
            case 19:  return splitPan.random[(int)d1].nextDouble();
            case 95:  {
                double d=Math.min(Math.abs(d1),d2);
                if(d1<0)d=-d;
                return d;
            }
            case 96:  {
                double d=Math.max(Math.abs(d1),d2);
                if(d1<0)d=-d;
                return d;
            }
            case 97:  {if(d1==d2)return 1; else return 0;}
            case 98:  return Math.min(d1,d2);
            case 99:  return Math.max(d1,d2);
        }
       return NaN;
   } 
  public static double factorial(double d){
      if(d==0)return 1;
      double d1=d-1;
      while (d1>0){d*=d1;d1--;}
      return d;
  }
   public static void debugcursorMinim(String s){
        System.out.println(">>>cursorMinim>>>>>>>"+s+"<<<<<<<");
        System.out.print("calculMinim: ");for(int i=0;i<Cal.longi;i++){System.out.print(i+": "+Cal.cursorMinim[i]+" _ ");}System.out.println();
        System.out.print("cursorMin:  ");for(int i=0;i<Cal.longi;i++){System.out.print(i+": "+Cur.cursorMinim[i]+" _ ");}System.out.println();
        System.out.print("cursGraf:      ");for(int i=0;i<Cal.longi;i++){System.out.print(i+": "+Cur.cursorCentreGrafic[i]+" _ ");}System.out.println();System.out.println();
        for(int i=0;i<Cal.longi;i++){if(cursorMinim[i]<0||cursorMinim[i]>segment)System.out.println("error<0 o >segment  index: "+i);}
   }
   public static void debugFunciodVO(String s){
        System.out.println(">>>dVO>>>>>>>"+s+"<<<<<<<");
        try{
            System.out.println("s_dVO.length: "+sVO.length+" "+dVO.length);
            for(int m=0;m<dvo.length;m++){System.out.print(sVO[m]);}System.out.println("");
            for(int m=0;m<dvo.length;m++){System.out.print(dVO[m]);}System.out.println("");
            for(int m=0;m<dvo.length;m++){System.out.print(Cur.sVO[m]);}System.out.println("");
            for(int m=0;m<dvo.length;m++){System.out.print(Cur.dVO[m]);}System.out.println("");
            for(int m=0;m<dvo.length;m++){System.out.print(Cur.dVOG[m]);}System.out.println("");
        }
        catch(Exception e){System.out.println("Exception");}
   }
   public static void debugFunciodVO_Cur(String s){
        System.out.println(">dVO> "+s+"<");
        try{
            System.out.println("s_dVO.length: "+Cur.sVO.length+" "+Cur.dVO.length);
            for(int m=0;m<Cur.dVO_.length;m++){System.out.print(Cur.sVO[m]);}System.out.println("");
            for(int m=0;m<Cur.dVO_.length;m++){System.out.print(Cur.dVO_[m]);}System.out.println("");
        }
        catch(Exception e){System.out.println("Exception");}
   }
   public static void testdVO_(String inf,String[] sv,double[] dv,double[] var,double[] resFil,double[] it){
       System.out.println(inf);
       int i=sv.length;if(i>100)i=100;
       int j=var.length;if(j>5)j=5;
        if(it!=null){for(int q=0;q<it.length;q++)System.out.print("("+q+")"+it[q]+"  ");System.out.println();}
        for(int q=0;q<resFil.length;q++)System.out.print("("+q+")"+resFil[q]+"  ");System.out.println();
        for(int q=0;q<j;q++)System.out.print("("+q+")"+var[q]+"  ");System.out.println();
        for(int q=0;q<i;q++)System.out.print("("+q+")"+sv[q]+"  ");System.out.println();
        for(int q=0;q<i;q++)System.out.print("("+q+")"+dv[q]+"  ");System.out.println();System.out.println();
   }
       void info(boolean b){
        System.out.println(b+" "+resultat);
        System.out.println("idxP: "+idxP);
        System.out.println("limSCalc: "+limSCalc);
        //System.out.println("resultatMcop");for(int i=0;i<pondIncrVar.length;i++)System.out.print(pondIncrVar[i]+" ");System.out.println();System.out.println();
        System.out.println("cursor");for(int i=0;i<cursor.length;i++)System.out.print(cursor[i]+" ");System.out.println();
        System.out.println("cursorMinim");for(int i=0;i<cursorMinim.length;i++)System.out.print(cursorMinim[i]+" ");System.out.println();
        System.out.println("varAct");for(int i=0;i<varAct.length;i++)System.out.print(varAct[i]+" ");System.out.println();
        System.out.println("dvo");for(int i=0;i<dvo.length;i++)System.out.print(dvo[i]+" ");System.out.println();
        System.out.println("dVO");for(int i=0;i<dvo.length;i++)System.out.print(dVO[i]+" ");System.out.println();
        System.out.println("sVO");for(int i=0;i<dvo.length;i++)System.out.print(sVO[i]+" ");System.out.println();
        //System.out.println("limSupCalc");for(int i=0;i<limSupCalc.length;i++)System.out.print(limSupCalc[i]+" ");System.out.println();
        //System.out.println("minIncrSignif");for(int i=0;i<minIncrSignif.length;i++)System.out.print(minIncrSignif[i]+" ");System.out.println();
        //System.out.println("resultatP");for(int i=0;i<resultatP.length;i++)System.out.print(resultatP[i]+" ");System.out.println();
        //System.out.println("resultatMcop");for(int i=0;i<resultatMcop.length;i++)System.out.print(resultatMcop[i]+" ");System.out.println();System.out.println();
    }
       static void info1(double[] ipv){
        for(int i=0;i<ipv.length;i++)System.out.print(ipv[i]+" ");System.out.println();
        for(int i=0;i<resultatP.length;i++)System.out.print(resultatP[i]+" ");System.out.println();
    }
   
   
}
