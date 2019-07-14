package Calc;

/** @author EFO
 */

import static java.lang.Double.NaN;
import static java.lang.Double.isFinite;
import static java.lang.Double.isNaN;
public class suportID{
    // ASSOCIAT a funcions parcials derivables
    public static long[] intervalCurDer;//valor numeric increment de la variable lliure pel calcul de la derivada
    public boolean[] hihaDependencia;
    boolean[][] dependencia;
    public double[] resultatFil;
    double resultat;
    public long cursor;
    double[] dVO;
    public static int[] darrerDigits;
    public void iniciD(int i){
        hihaDependencia=new boolean[i];
        dependencia=new boolean[i][i];
        if(taulaD.hihaDependencies)reiniciDepen();
        resultatFil=new double[i];System.arraycopy(taulaD.resultatFil, 0, resultatFil, 0, i);
    }
    public void estimarIncrement(int fil){
        if(taulaD.idxVarDer[fil]<0)return;
        if(taulaD.hihaInterval[fil]){
            int i=(int)taulaD.intervDer[fil];
            if(i<6)i=6;
            if(i>42)i=42;
            intervalCurDer[fil]=(long)(Cal.segment/Math.pow(2,i));
            //for(int j=0;j<intervalCurDer[0].length;j++)intervalCurDer[fil][j]=(long)(Cal.segment/Math.pow(2,i));
            return;
        }
        long[] cursor=new long[Cal.cursor.length];
        for(int i=0;i<cursor.length;i++)cursor[i]=Cal.segment/2;
        int exponent=0;
        int digits=0;
        int conta=0;
        for(int pos=-1;pos<2;pos+=2)for(int idx=2;idx<16;idx++){
            for(int cur=0;cur<cursor.length;cur++)cursor[cur]+=Cal.segment*pos/idx;
            idx*=2;
            for(int i=5;i<54;i++){
                long incr=(long)(Cal.segment/Math.pow(2,i));
                double digitsValids=estimarIncrement(fil,cursor,incr);
                if(digitsValids>2){digits+=digitsValids;exponent+=i;conta++;}
            }
        }
        if(conta==0){exponent=6;digits=-1;}
        else{exponent/=conta;digits/=conta;}
        intervalCurDer[fil]=(long)(Cal.segment/Math.pow(2,exponent));
        //for(int j=0;j<intervalCurDer[0].length;j++)intervalCurDer[fil][j]=(long)(Cal.segment/Math.pow(2,exponent));
        if(darrerDigits[fil]!=digits){
            darrerDigits[fil]=digits;
            if(digits<5){Func.append(1,Func.avis);Func.append(0,"el calcul gr"+Func.rB.getString("a")+"fic de la derivada de la funci"+Func.rB.getString("o_")+" de la filera: "+fil+" amb ordre derivada = "+taulaD.ordreDer[fil]+" requereix de un interval gran en un ampli marge de punts, estmacio de mitjana digits valids: "+(int)digits+ splitPan.FIL);}
            else if(digits<3){Func.append(1,Func.avis);Func.append(0,"el calcul gr"+Func.rB.getString("a")+"fic de la derivada de la funci"+Func.rB.getString("o_")+" de la filera: "+fil+" amb ordre derivada = "+taulaD.ordreDer[fil]+" requereix de un interval gran en un ampli marge de punts, el resultat pot probablement esser poc fiable; estimaci"+Func.rB.getString("o_")+" mitjana d"+Func.rB.getString("i_")+"gits v"+Func.rB.getString("a")+"lids: "+(int)digits+ splitPan.FIL);}
            else if(digits<1){Func.append(1,Func.avis);Func.append(0,"el calcul gr"+Func.rB.getString("a")+"fic de la derivada de la funci"+Func.rB.getString("o_")+" de la filera: "+fil+" amb ordre derivada = "+taulaD.ordreDer[fil]+" requereix de un interval molt gran en un ampli marge de punts, el resultat molt probablement sera una aproximaci"+Func.rB.getString("o_")+"; mitjana d"+Func.rB.getString("i_")+"gits v"+Func.rB.getString("a")+"lids: "+(int)digits+ splitPan.FIL);}
        }
    }
    private double estimarIncrement(int fil,long[] cursor,long incr){
        double[] varAct=new double[Cur.varAct.length];
        System.arraycopy(Cur.varAct,0,varAct,0,varAct.length);
        double[] fun=new double[taulaD.ordreDer[fil]+1];
        double[] dv=new double[fun.length];
        int i0=taulaD.idxVarDer[fil];
        cursor[i0]-=(incr*(fun.length-1))/2;
        for(int i=0;i<fun.length;i++){
            varAct[i0]=Cal.posic(taulaD.idxVarDer[fil],cursor[i0]);
            fun[i]=calculF(varAct,fil);
            cursor[i0]+=incr;
            dv[i]=15;
        }
        estimarIncrement(fun,dv);
        return dv[0];
    }
    private void estimarIncrement(double[] fun,double[] dv){
        double[] f=new double[fun.length-1];
        for(int i=1;i<fun.length;i++){
            f[i-1]=fun[i]-fun[i-1];
            dv[i-1]-=Math.log10(Math.abs((fun[i]+fun[i-1])/(2*f[i-1])));
            if(!isFinite(dv[i-1])){dv[i-1]=0;return;}//retorna 0 digits valids
        }
        if(f.length>1)estimarIncrement(f,dv);
    }
    /*public void calculIniciIncrements_(){//desde Cur s'executa un cop al iniciar el calcul a Cur.graficInicial
        for(int idxFil=0;idxFil<taulaD.simbol.length;idxFil++)estimarIncrement(idxFil);
        return;   
    }*/
    public void reiniciDepen(){
        for(int i=0;i<taulaD.simbol.length;i++)System.arraycopy(taulaD.dependencia[i],0, dependencia[i], 0, dependencia[0].length);
        System.arraycopy(taulaD.hihaDependencia,0, hihaDependencia, 0, taulaD.simbol.length);
    } 
    public void reiniciDep1Var(int o){
       dependencia=new boolean[taulaD.simbol.length][taulaD.simbol.length];
       hihaDependencia=new boolean[taulaD.simbol.length];
       for(int i=0;i<taulaD.simbol.length;i++)if(taulaD.VGaFuncBol[i][o]){
           hihaDependencia[i]=true;
           for(int j=i+1;j<taulaD.simbol.length;j++)if(taulaD.dependencia[j][i])dependencia[j][i]=true;
       }
    }
    //ATENCIO quan es pasa una matriu a un procediment amb un nom nou si es modifica la matriu nova la original queda modificada
    //en el cas de varAct aquesta es modifica pero recupera el valor original a calculDerivada(double[] varAct,int idxFil,long c)
    //cur e crea una nova matriu c que agafa els valors del curs
    public double[] calculDerivada(double[] varAct,long[] cur){//procediment util quant s'han modificat totes les variables ex: procediment actualitzasVO()//es reinicien les dependencies i s'executa el calcul complet de totes les fileres començant per la primera filera;
        if(taulaD.hihaDependencies)reiniciDepen();
        if(Func.tipusFuncioBol){
            for(int i=0;i<taulaD.simbol.length;i++)if(taulaF.varGenDerBol[taulaF.fila][i+1]){//taulaF.varGenDerBol: hi ha algun simbols de funcio parcial (que directa o indirectament contenent variables generals) presents a la funcio de la filera i; si a la fila de taulaF hi ha variable o funcio parcial dependent de variable s'executa
                if(taulaD.ordreDer[i]==0)resultatFil[i]=calculF(varAct,i);
                else resultatFil[i]=calculDer(varAct,i,taulaD.idxVarDer[i],cur);
                if(taulaD.hihaDependencies&&i<taulaD.simbol.length-1)actualSegFil(resultatFil[i],i);//si true trasllada el valor obtingul de la filera idxFil a les fileres seguents que depenent d'aquesta
            }
        }
        else{
            for(int i=0;i<taulaD.simbol.length;i++)if(taulaD.hihaVGioFP[i]){//si hi ha variable o funcio parcial dependent de variable (no es una constant desde l'inici) s'executa
                if(taulaD.ordreDer[i]==0)resultatFil[i]=calculF(varAct,i);
                else resultatFil[i]=calculDer(varAct,i,taulaD.idxVarDer[i],cur);
                if(taulaD.hihaDependencies&&i<taulaD.simbol.length-1)actualSegFil(resultatFil[i],i);//si true trasllada el valor obtingul de la filera idxFil a les fileres seguents que depenent d'aquesta
            }
        }
        return resultatFil;
    }
    //el calcul de les funcions de fileres sense ordre implica que quant sinicia el conjunt de calculs de les fileres
    //cal que previament es reinici si hihaDependencies es true: reiniciDepen(); dede Cur io Cal
    public double[] calculDerivada(int idxVar,double[] varAct,long[] cur){//idxVar es l'index de les variables generals que es modifica es correspont amb l'index de cursor
        //quant es modifica una variable les fileres afectades no tenent perque seguir l'ordre de les fileres
        //si es el primer calcul de un conjunt en que es modifica la mateixa variable per les fileres anteriors dependents encara que no tinguin la variable
        //a continuacio  fa el calcul nomes per les fileres afectades per la variable començant per la primera filera
        if(taulaD.hihaDependencies)reiniciDepen();//reiniciDep1Var(idxVar);
        if(Func.tipusFuncioBol){
            for(int i=0;i<taulaD.simbol.length;i++)if(taulaF.varGenDerBol[taulaF.fila][i+1]){
                if(taulaD.ordreDer[i]==0/*||taulaD.idxVarDer[i]!=idxVar*/)resultatFil[i]=calculF(varAct,i);
                else resultatFil[i]=calculDer(varAct,i,idxVar,cur);
                if(taulaD.hihaDependencies&&i<taulaD.simbol.length-1)actualSegFil(resultatFil[i],i);//si true trasllada el valor obtingul de la filera idxFil a les fileres seguents que depenent d'aquesta
            }
        }
        else{
            for(int i=0;i<taulaD.simbol.length;i++){
                if(taulaD.VGaFuncBol[i][idxVar]){
                    if(taulaD.ordreDer[i]==0)resultatFil[i]=calculF(varAct,i);
                    else resultatFil[i]=calculDer(varAct,i,idxVar,cur);
                    if(taulaD.hihaDependencies&&i<taulaD.simbol.length-1)actualSegFil(resultatFil[i],i);//si true trasllada el valor obtingul de la filera idxFil a les fileres seguents que depenent d'aquesta
                }
            }
        }
        return resultatFil;
    }
    //A calcul derivada si la funcio no es derivada es calcula la funcio mitjançant calculFun que fa un calcul previ de totes les funcions que l'afecten i no hen estat previament calculades
    //quan es una funcio derivada el primer calcul es fa igual, pero els seguents es fan mitjançant calculDer que afecte nomes les funcions en que interve la variable derivable (les altres es mantenent constants
    public double calculDer(double[] varAct,int idxFil,int idxVar,long[] cursor){//idxVar es l'index de la variable activa no de la variable derivada'
        //if(taulaD.ordreDer[idxFil]==0)resultatFil[idxFil]=calculF(varAct,idxFil);
        //si no es calcula la derivada el calcul es el de la funcio com que el calcu es fa en la direccio de la primera a l'ultima filera totes les funcions previes afectades estaran calculades
        //si hi ha derivada no conte funcions parcials per tant no hi funcions de fileres anteriors afectades
        int i0=taulaD.idxVarDer[idxFil];
        long i1=intervalCurDer[idxFil];
        double vAct=varAct[i0];
        int o=taulaD.ordreDer[idxFil]+1;
        double[] fun=new double[o];//calcula la funcio per un nombre de intervals = ordre derivada +1
        long p=taulaD.ordreDer[idxFil]*i1/2;
        if(idxVar!=i0)varAct[idxVar]=Cal.posic(i0,cursor[idxVar]);
        long c=cursor[i0];
        if(c+p>Cal.segment) c=Cal.segment-p;
        long cur=c-p;
        if(cur<0)cur=0;
        varAct[i0]=Cal.posic(i0,cur);
        double incr=varAct[i0];
        fun[0]=calculF(varAct,idxFil);//el primer calcul de la funcio cal que inclogui les funcions dependents que no contenen la variable sobre la que es deriva
        varAct[i0]=Cal.posic(i0,cur+i1);
        fun[1]=calculF(varAct,idxFil);
        incr=varAct[i0]-incr;
        for(int i=2;i<fun.length;i++){
            varAct[i0]+=incr;
            fun[i]=calculF(varAct,idxFil);
        }
        varAct[i0]=vAct;//la variable actual pren el valor anterior al calcul
        resultatFil[idxFil]=calculD(fun,incr);
        return resultatFil[idxFil];
    }
    public double calculD(double[] fun,double incr){
       int cont=fun.length;
       for(int j=1;j<fun.length;j++){
            for(int i=1;i<cont;i++){
                fun[i-1]=(fun[i]-fun[i-1])/incr;
            }
       cont--;
       }
        return fun[0];
    }
    void actualSegFil(double d,int idx){
        for(int i=idx+1;i<taulaD.simbol.length;i++){
            if(dependencia[i][idx]){
                for(int j=taulaD.limFPD[i][0];j<taulaD.limFPD[i][1];j++){
                    if(taulaD.indexsFPD[j][1]==idx){
                        dVO[taulaD.indexsFPD[j][0]]=d;
                    }
                }dependencia[i][idx]=false;
            }
        }
        hihaDependencia[idx]=false;
    }
    public double calculF(double[] varAct,int idxFil){
        int i0=taulaD.derivadesLimFun[idxFil][0];int i1=taulaD.derivadesLimFun[idxFil][1]-i0;
        double[]dvo=new double[i1];System.arraycopy(dVO,i0,dvo,0,i1);
        String[]svo=new String[i1];System.arraycopy(taulaD.sVO,i0,svo,0,i1);
        i0=taulaD.derivadesLimFun[idxFil][4];i1=taulaD.derivadesLimFun[idxFil][5];//limits de Cal.indexsVar.length
        for(int i=i0;i<i1;i++){
            if(svo[taulaD.indexsVGaD[i][0]].startsWith("-"))dvo[taulaD.indexsVGaD[i][0]]=-varAct[taulaD.indexsVGaD[i][1]];
            else dvo[taulaD.indexsVGaD[i][0]]=varAct[taulaD.indexsVGaD[i][1]];
        }
        i0=taulaD.derivadesLimFun[idxFil][2];i1=taulaD.derivadesLimFun[idxFil][3];
        double d=NaN;
        if(i1==i0) {
            for(int i=0;i<dvo.length;i++)if(isFinite(dvo[i])){d=dvo[i];return d;}
            return NaN;
        }
        else{
            for(int i=i0;i<i1;i++){dvo[taulaD.indexs[i][1]]=Cal.caseOpFun(taulaD.indexs[i][0],dvo[taulaD.indexs[i][1]],dvo[taulaD.indexs[i][2]]);}
            d=dvo[taulaD.indexs[i1-1][1]];
        }
        if(!isFinite(d))return NaN;
        return d;
    }
    public double calculF(int idxFil){//quant la funcio no conte variables generals pero hi ha variables de la taulaF
        int i0=taulaD.derivadesLimFun[idxFil][0];int i1=taulaD.derivadesLimFun[idxFil][1]-i0;
        double[]dvo=new double[i1];System.arraycopy(dVO,i0,dvo,0,i1);
        String[]svo=new String[i1];System.arraycopy(taulaD.sVO,i0,svo,0,i1);
        i0=taulaD.derivadesLimFun[idxFil][2];i1=taulaD.derivadesLimFun[idxFil][3];//limits de Cal.indexs.length
        double d=NaN;
        if(i1==i0) {
            for(int i=0;i<dvo.length;i++)if(isFinite(dvo[i])){d=dvo[i];}
        }
        else{
            for(int i=i0;i<i1;i++){dvo[taulaD.indexs[i][1]]=Cal.caseOpFun(taulaD.indexs[i][0],dvo[taulaD.indexs[i][1]],dvo[taulaD.indexs[i][2]]);}
            d=dvo[taulaD.indexs[i1-1][1]];
        }
        if(!isFinite(d))return NaN;
        if(taulaD.hihaDependencies){reiniciDepen();actualSegFil( d,idxFil);}//si true trasllada el valor obtingul de la filera idxFil a les fileres seguents que depenent d'aquesta
        return d;
    }
    
    
    
    
    //Integral.integralLimitsFun[l][6 i 7]son el limits inferior i superior dins la matriu Integral.sVOI de cada sumatori (inclou funcio principal i funcions limits)
    //Integral.integralLimitsFun[l][0 i 1]son els limits de les funcions principals dels sumatoris
    //veure taulaI integralLimitsFun i integralLimits a procediment iniFuncionsVariables()
    public static boolean[][][] integralVaraLimitsBol;//indica si hi ha variable als limits 0:inicial 1:final o 2:a qualsevol dels dos 
    public static boolean[] hihaFPambVGaI;
    public static boolean[] hihaVGioFPaI;//boolean si [i]= true: hi ha variable general o funcions parcials a sumatori de columna, associat a variable general a la integral limits o funcio de la integral del index; aTaulaF fa false els sumatoris que no es troben a la filera per limitar els calculs de sumatoris que no apareixwn a la funcio ( Cal.actualitzadVO())
    public static boolean[] hihaVG_aI;//boolean hi ha variable general a la integral limits o funcio de la integral del index
    public static boolean hihaVG_aTOTI;///boolean hi ha variable general al conjunt de integrals?
    public static boolean[] hihaVtaulaF_aI;
    public static int[] longi;
    public static int longiM;
    public static double [][] ceroLog;
    public static boolean[][] logaritmeBol;
    public static int [][] matriuInt;
    public static int[][][][] integralLimits;
    public static int[][] integralLimitsFun;
    public static String[] sVOI;
    public static int[][] indexsVGaI;//aqui indexsVGaI  conte a: 0 la posicicio dins dVOI, a: 1 el index de la variable general a taulaF corretgit per cada filera
    static int[] idxVarGenIntegr;//les variables generals presents a integrals//splitPan.idxVG;//index de les variables generals que es troben a la funcio principal
    public double[] integral;//conte el valor del sumatoris
    public double[] dVOI;
    double[] dvoLI;
    String[] svoLI;
    double[] dvoF;
    String[] svoF;
    int[][] indexsI;
    int[][] indexsVarI;
    int[][] indexs;
    int[][] indexsVar;
    int[][] indexsF;//indexs de les operacions dins la funcio superior; copia parcial de  Integral.indexsVarI 
    int[][] indexsVarF;//indexs de les variables internes dins la funcio superior; copia parcial de  Integral.indexsVarI que conte els index de totes les integrals
    public double [][][] dMatriuVar;//aqui es diferent a dmatriuVar[][][2] de Cur a Cur es la difrencia [1]-[0] aqui es el nombe de intervals
    public double [] dMatriuFunc;
    public long[] segCero;
    Thread integralThread;
    Thread tempsThread;
    long[] incrAbc,incrAbc_mitat;
    int[] limitAbc;
    double[] varActLI;
    double[] varActLI_mes;
    double[] varActLI_menys;
    double[] varActLI_incr;
    int[] idxDim;
    int idxCol=0;
    double[] varAct;//aqui varAct no te relacio amb varCat a Cur i Cal relacionats amb les variables Generals i parametres
    public static int[][]  indexsFPIntegr;//[][0] index de dVOI; [][1] index del simbol de la taula de funcions parcials
    public void longiMesLLarg(){//la matriu segCero i altres  es fa de una longitud igual a la integral amb mes variables
        longiM=0;
        for(idxCol=0;idxCol<longi.length;idxCol++)if(longi[idxCol]>longiM)longiM=longi[idxCol];
        idxDim=new int[longiM];
        varAct=new double[longiM];
        varActLI=new double[longiM];
        limitAbc=new int [longiM];
        incrAbc=new long [longiM];//.segment/limitAbc;
        incrAbc_mitat=new long [longiM];//incrAbc/2;
        varActLI_mes=new double[longiM];
        varActLI_incr=new double[longiM];
        varActLI_menys=new double[longiM];
        segCero=new long[longiM];
    }
    public void FPaIntegral(){
        if(taulaD.hihaDades){
            int cont=0;
            for(int j=0;j<taulaD.simbol.length;j++){
                for(int i=0;i<sVOI.length;i++){
                    if(sVOI[i].equals(taulaD.simbol[j])||sVOI[i].equals("-"+taulaD.simbol[j]))cont++;
                }
            }
            if (cont>0){ 
                Cal.hihaFPIntegrBol[0][0]=true;//boolean de [sumat.length+1][simbols.length+1] que relaciona sumatoris qur contenent funcionsParcials no constants
                indexsFPIntegr=new int[cont][2];
                cont=0;
                for(int k=0;k<integralLimitsFun.length;k++){
                    for(int i=integralLimitsFun[k][0];i<integralLimitsFun[k][7];i++){
                        for(int j=0;j<taulaD.simbol.length;j++){
                            if(sVOI[i].equals(taulaD.simbol[j])||sVOI[i].equals("-"+taulaD.simbol[j])){
                                indexsFPIntegr[cont][0]=i;indexsFPIntegr[cont][1]=j;cont++;
                                Cal.hihaFPIntegrBol[k+1][j+1]=true;
                            }
                        }
                    }
                }
                for(int i=0;i<dVOI.length;i++){
                    for(int j=0;j<taulaD.simbol.length;j++){
                        if(sVOI[i].equals(taulaD.simbol[j])||sVOI[i].equals("-"+taulaD.simbol[j]))dVOI[i]=1;
                    }
                }
            }
            else{indexsFPIntegr=new int[0][0];}
        }
    }
    public void FPIntegral(){
        for(int j=0;j<indexsFPIntegr.length;j++){
            if(sVOI[indexsFPIntegr[j][0]].startsWith("-"))dVOI[indexsFPIntegr[j][0]]=-resultatFil[indexsFPIntegr[j][1]];
            else dVOI[indexsFPIntegr[j][0]]=resultatFil[indexsFPIntegr[j][1]];
        }
    }
    public void FPaIntegral(int idxFP){
        for(int j=0;j<indexsFPIntegr.length;j++)if(indexsFPIntegr[j][1]==idxFP){
            if(sVOI[indexsFPIntegr[j][0]].startsWith("-"))dVOI[indexsFPIntegr[j][0]]=-resultatFil[indexsFPIntegr[j][1]];
            else dVOI[indexsFPIntegr[j][0]]=resultatFil[indexsFPIntegr[j][1]];
        }
    }
    public void substitueixVG(double[] d){//substitueix totes les variables generals a totes les integrals(funcions i intervals) no a la funcio principal general (Cur.indexVGI)
        for(int i=0;i<indexsVGaI.length;i++){
            if(sVOI[indexsVGaI[i][0]].startsWith("-"))dVOI[indexsVGaI[i][0]]=-d[indexsVGaI[i][1]];
            else dVOI[indexsVGaI[i][0]]=d[indexsVGaI[i][1]];
        }
    }
    //per calcular els limits inferiors superiors de les funcions amb variables cal saber el valor de les variables
    //la primera filera de la taula variables ha d'esser nomes numerica
    //per les seguents fileres cal que les variables de les funcions ja estiguin definides en fileres anteriors
    //el valor de les variables de la filera actual han d'estar definides en el cicle anterior
    public void buclIntegral(int i){
        Cal.stopBucle=false;
        tempsThread = new Thread(new tempsThread()); tempsThread.setPriority(Thread.MIN_PRIORITY);tempsThread.start();
        if(i==1){integralThread = new Thread(new bucleIntegral1()); integralThread.setPriority(Thread.MAX_PRIORITY);integralThread.start();}
        else if(i==2){integralThread = new Thread(new bucleIntegral2()); integralThread.setPriority(Thread.MAX_PRIORITY);integralThread.start();}
    }
    class tempsThread implements Runnable{
        public void run() {
            while(!Cal.stopBucle){
                try{Thread.sleep(100L);} 
                catch( InterruptedException e) {}
            }
            while(integralThread.isAlive()){
                try{Thread.sleep(50L);} 
                catch( InterruptedException e) {}
            }
            Cal.stopBucle=false;
            Func.falseTrue();
        }
    }
    class bucleIntegral1 implements Runnable {//hi ha variables generals no hi ha sumatoris a la funcio principal
        public void run() {
            int p=Cur.puntsGraf[Cur.indexPunts];
            for(idxCol=0;idxCol<longi.length;idxCol++){
                if(!hihaVGioFPaI[idxCol]){
                    taulaI.sumatConstants[idxCol]=true;
                    bucle(idxCol);infoTemps();
                    if(!taulaI.sumatConstants[idxCol]){
                        taulaI.sumatConstants[idxCol]=true;
                        bucle(idxCol);
                        splitPan.calc.supID.integral[idxCol]=integral[idxCol];
                        infoTemps();
                    }
                }
            }
            for(int k=0;k<=p;k++){
                for(int j=0;j<idxVarGenIntegr.length;j++){
                       long l=k*(Cal.segment/p);
                       Cal.varAct[idxVarGenIntegr[j]]=Cal.posic(idxVarGenIntegr[j],l);
                }
                substitueixVG(Cal.varAct);//substitueix dVOI per valor de variablesGenerals Cal.VarAct
                for(idxCol=0;idxCol<longi.length;idxCol++)if(hihaVGioFPaI[idxCol])bucle(idxCol);
            }
            Cal.stopBucle=true;
        }
    }
    class bucleIntegral2 implements Runnable {//hi ha variables generals i sumatoris a la funcio principal
        public void run() {//aquest thread cal perque aquest thread s'executi en paralel a un calcul
            for(idxCol=0;idxCol<longi.length;idxCol++){
                if(!hihaVGioFPaI[idxCol]){
                    if(!taulaI.sumatConstants[idxCol]){
                        taulaI.sumatConstants[idxCol]=true;
                        bucle(idxCol);
                        splitPan.calc.supID.integral[idxCol]=integral[idxCol];
                        infoTemps();
                    }
                }
            }
            Cur.graficInicial();
            Cal.stopBucle=true;
        }
    }
    public void infoTemps(){
        if(!isFinite(integral[idxCol]))Func.append(2,taulaI.sumat[idxCol]+": NaN  ");
        else {
            if(Func.noArrodonir)Func.append(2,taulaI.sumat[idxCol]+": "+integral[idxCol]+"  ");
            else Func.append(2,taulaI.sumat[idxCol]+": "+splitPan.rodo(integral[idxCol])+"  ");
        }
        if(Func.ampliarInfo){
                    Func.append(0,"  (temps: "+splitPan.temp()+" seg.,  acumulat: "+splitPan.tempt()+" seg.)"+splitPan.FIL);
                    splitPan.temp0();
                }
        else Func.append(2,splitPan.FIL);
    }
    public void bucle(int idx){//el bucle s'executa per cada idxCol (integral)
        idxCol=idx;
        integral[idxCol]=0;
        for(int i=0;i<longi[idxCol];i++){
            limitAbc[i]=matriuInt[i][idxCol];
            incrAbc[i]=Cal.segment/limitAbc[i];//.segment/limitAbc;
            incrAbc_mitat[i]=incrAbc[i]/2;
        }
        for(int j=0;j<longi[idxCol];j++){
            if(logaritmeBol[j][idxCol]){
                segCero[j]=0;
                escalaNoLinial(j);
            }
            else{
                if(!integralVaraLimitsBol[idxCol][j][2]){
                    varActLI_incr[j]=dMatriuVar[idxCol][j][2]/limitAbc[j];
                }
            }
        }
        int i0=integralLimitsFun[idxCol][0];
        int i1=integralLimitsFun[idxCol][1]-i0;
        dvoF=new double[i1];//System.arraycopy(dVOI,i0,dvoF,0,i1);
        svoF=new String[i1];System.arraycopy(sVOI,i0,svoF,0,i1);
        i0=integralLimitsFun[idxCol][2];
        i1=integralLimitsFun[idxCol][3]-i0;
        indexsF=new int[i1][3];
        for (int i=0;i<i1;i++){System.arraycopy(indexsI[i+i0], 0, indexsF[i], 0, 3);}
        i0=integralLimitsFun[idxCol][4];
        i1=integralLimitsFun[idxCol][5]-i0;
        indexsVarF=new int[i1][2];
        for (int i=0;i<i1;i++){System.arraycopy(indexsVarI[i+i0], 0, indexsVarF[i], 0, 2);}
        cercleIntegralLinial(-1);
    }
    private void cercleIntegralLinial(int ct){//calcula el sumatori de una columna (la definida a bucle com idx)
        if(Cal.stopBucle){return;}
        ct++;//ct es correspont amb les files del sumatori definit com idxCol=idx a bucle
        if(integralVaraLimitsBol[idxCol][ct][2]){
            if(integralVaraLimitsBol[idxCol][ct][0]){dMatriuVar[idxCol][ct][0]=resultatLI(ct,0);}
            if(integralVaraLimitsBol[idxCol][ct][1]){dMatriuVar[idxCol][ct][1]=resultatLI(ct,1);}
            if(dMatriuVar[idxCol][ct][0]>dMatriuVar[idxCol][ct][0]){
                double d=dMatriuVar[idxCol][ct][0];
                dMatriuVar[idxCol][ct][0]=dMatriuVar[idxCol][ct][1];
                dMatriuVar[idxCol][ct][1]=d;
            }
            dMatriuVar[idxCol][ct][2]=dMatriuVar[idxCol][ct][1]-dMatriuVar[idxCol][ct][0];
            if(logaritmeBol[ct][idxCol])escalaNoLinial(ct);
            else varActLI_incr[ct]=(dMatriuVar[idxCol][ct][1]-dMatriuVar[idxCol][ct][0])/limitAbc[ct];
        }
        
        for(idxDim[ct]=0;idxDim[ct]<limitAbc[ct];idxDim[ct]++){// si hi ha tres intervals idxDim[ct] pren els valors 0,1,2,
            if(Cal.stopBucle){return;}
            varActLI[ct]=posic(ct,incrAbc[ct]*idxDim[ct]+incrAbc_mitat[ct]);//si hi ha 3 intervals varActLI prent els valors 0.5,1.5,2.5 situat en el centre dels intervals
            if(logaritmeBol[ct][idxCol]){
                    if(idxDim[ct]==0)varActLI_menys[ct]=posic(ct,incrAbc[ct]*idxDim[ct]);
                    varActLI_mes[ct]=posic(ct,incrAbc[ct]*idxDim[ct]+incrAbc[ct]);
                    varActLI_incr[ct]=varActLI_mes[ct]-varActLI_menys[ct];
                    varActLI_menys[ct]=varActLI_mes[ct];
            }
            if(ct<longi[idxCol]-1){cercleIntegralLinial(ct);}//quant s'arriba a la darrera fila s'ha  substituit totes les variables de les fileres precedents
            else{
                //cercleLimits(idxDim);
                //s'executa quan ct=Cal.longi-1 que es la darrera variable i per tant s'han calculat les funcions
                /*System.out.println(">>>"+ct+" "+idxDim[ct]);
                System.out.print("incrAbc[ct]*idxDim[ct]+incrAbc_mitat[ct]:  ");for(int k=0;k<longi[idxCol];k++){System.out.print(incrAbc[ct]*idxDim[ct]+" "+incrAbc_mitat[ct]+" _ ");}System.out.println();
                System.out.print("varActLI:  ");for(int k=0;k<longi[idxCol];k++){System.out.print(varActLI[k]+" ");}System.out.println();
                System.out.print("varActLI_mes:  ");for(int k=0;k<Cal.longi;k++){System.out.print(varActLI_mes[k]+" ");}System.out.println();
                System.out.print("varActLI_menys:  ");for(int k=0;k<Cal.longi;k++){System.out.print(varActLI_menys[k]+" ");}System.out.println();
                System.out.print("varActLI_incr:  ");for(int k=0;k<longi[idxCol];k++){System.out.print(varActLI_incr[k]+" ");}System.out.println();
                System.out.print("dMatriuVar:  ");for(int k=0;k<longi[idxCol];k++){System.out.print(k+":  "+dMatriuVar[idxCol][k][0]+"  "+dMatriuVar[idxCol][k][1]+"  "+dMatriuVar[idxCol][k][2]+"  |  "+dMatriuVar[idxCol][k][3]+"  "+dMatriuVar[idxCol][k][4]+"  "+dMatriuVar[idxCol][k][5]+"  |||  ");}System.out.println();
                */
                for(int k=0;k<longi[idxCol];k++){varAct[k]=posic(k,incrAbc[k]*idxDim[k]+incrAbc_mitat[k]);}
                double res=resultatF();
                for(int k=0;k<longi[idxCol];k++)res*=varActLI_incr[k];
                //for(int k=0;k<longi[idxCol];k++)res*=Math.abs(varActLI_incr[k]);//els increments son positius
                integral[idxCol]+=res;
            }
        }
    }
    private void cercleLimits(int[] idx){
        System.out.println();
        for(int i=0;i<longi[idxCol];i++){
            System.out.print("cercle: ");System.out.print(idx[i]+" ");}
        System.out.println();
    }
    public void escalaNoLinial(int i){
        //double min=2.4703282292062327209E-324;
        if(dMatriuVar[idxCol][i][0]==0){
            if(dMatriuVar[idxCol][i][1]<1)dMatriuVar[idxCol][i][3]=Math.log10(dMatriuVar[idxCol][i][1])+ceroLog[i][idxCol];
            else dMatriuVar[idxCol][i][3]=ceroLog[i][idxCol];
            dMatriuVar[idxCol][i][0]=Math.pow(10,dMatriuVar[idxCol][i][3]);
        }
        else{dMatriuVar[idxCol][i][3]=Math.log10(Math.abs(dMatriuVar[idxCol][i][0]));}
        if(dMatriuVar[idxCol][i][1]==0){
            if(dMatriuVar[idxCol][i][0]>-1)dMatriuVar[idxCol][i][4]=Math.log10(-dMatriuVar[idxCol][i][0])+ceroLog[i][idxCol];
            else dMatriuVar[idxCol][i][4]=ceroLog[i][idxCol];
            dMatriuVar[idxCol][i][1]=-Math.pow(10,dMatriuVar[idxCol][i][4]);
        }
        else{dMatriuVar[idxCol][i][4]=Math.log10(Math.abs(dMatriuVar[idxCol][i][1]));} 
        if(dMatriuVar[idxCol][i][0]<0&&dMatriuVar[idxCol][i][1]>0){
            double mi=dMatriuVar[idxCol][i][3];
            if(mi>dMatriuVar[idxCol][i][4])mi=dMatriuVar[idxCol][i][4];
            if(mi>=0)mi=ceroLog[i][idxCol];//si el minim valor te un exponent positiu(log(var)>0 el cero sera 1E-16
            else mi=mi+ceroLog[i][idxCol];//si el minim valor te un exponent negatiu(log(var)<0 el cero sera 1E-16+expnent negatiu
            if(mi<-323.3062153431158)mi=-323.3062153431158;//double dou=0.2470999999999999E-323; log=-323,3062153431158
            double num=0.0;
            num=-mi+dMatriuVar[idxCol][i][3];
            segCero[i]=Math.round(Cal.segment*(num/(-2*mi+dMatriuVar[idxCol][i][4]+dMatriuVar[idxCol][i][3])));
            dMatriuVar[idxCol][i][6]=-mi+dMatriuVar[idxCol][i][4];
            dMatriuVar[idxCol][i][4]=mi;
            dMatriuVar[idxCol][i][5]=num;
        }
        else {
            if(dMatriuVar[idxCol][i][0]>0)dMatriuVar[idxCol][i][5]=dMatriuVar[idxCol][i][4]-dMatriuVar[idxCol][i][3];
            else dMatriuVar[idxCol][i][5]=-dMatriuVar[idxCol][i][4]+dMatriuVar[idxCol][i][3];
        }
    }
    //x=3 log del limit inferior
    //x=4: log limit superior o cero funcional positiu o negatiu (mi, taulaC.ceroLog)
    //x=5: interval entre log dels (limits inf i sup,  o limit inferior i cero funcional)
    //x=6: -1 o logaritme limit superior
    // en el cas de les integrals el limit inferior i superior poden esser funcions el limit inferior pot esser mes gran que el superior
    public double posic(int i,long m){//entra possicio  (long)cursor o long  i retorna valor abcisa 
        if(logaritmeBol[i][idxCol]){
            if(dMatriuVar[idxCol][i][0]>0) return Math.pow(10,dMatriuVar[idxCol][i][3]+dMatriuVar[idxCol][i][5]*m/Cal.segment);
            else if(dMatriuVar[idxCol][i][1]<0) return -Math.pow(10,dMatriuVar[idxCol][i][3]-dMatriuVar[idxCol][i][5]*m/Cal.segment);
            else{
                if(m>=segCero[i]) return Math.pow(10,dMatriuVar[idxCol][i][4]+dMatriuVar[idxCol][i][6]*(m-segCero[i])/(Cal.segment-segCero[i]));
                else return -Math.pow(10,dMatriuVar[idxCol][i][3]-dMatriuVar[idxCol][i][5]*(m)/(segCero[i]));
            }
        }
        else return dMatriuVar[idxCol][i][0]+dMatriuVar[idxCol][i][2]*m/Cal.segment;
    }
   public static int intA_corr(int intA,long pref){
    	Double f=(double)Math.round(Math.pow(intA,Cal.longi));
    	if(f>pref){
        	f=1.0/Cal.longi;
        	intA=(int)Math.pow(pref,f);
    	}
    	return intA;
    }
    public double resultatF(){
        actualitzarF();
        return calculF();
    }
    public void actualitzarF(){
        int i0=integralLimitsFun[idxCol][0];
        int i1=integralLimitsFun[idxCol][1]-i0;
        System.arraycopy(dVOI,i0,dvoF,0,i1);
        for(int i=0;i<indexsVarF.length;i++){
            if(svoF[indexsVarF[i][0]].startsWith("-"))dvoF[indexsVarF[i][0]]=-varAct[indexsVarF[i][1]];
            else dvoF[indexsVarF[i][0]]=varAct[indexsVarF[i][1]];
        }
    }
    public double calculF(){
        if(indexsF.length==0){
            if(dvoF.length==1) {return dvoF[0];}
            return dMatriuFunc[idxCol];
        }
        for(int i=0;i<indexsF.length;i++){
            dvoF[indexsF[i][1]]=Cal.caseOpFun(indexsF[i][0],dvoF[indexsF[i][1]],dvoF[indexsF[i][2]]);
        }
        double d=dvoF[indexsF[indexsF.length-1][1]];
        if(!isFinite(d)){
            actualitzarF();
            errorCalcul(svoF,dvoF,indexsF," funci"+Func.rB.getString("o_")+Func.sumat+idxCol,-1);
        }
        return d;
    }
    private void nousLimits(int cont,int Col){
        int i0=integralLimits[idxCol][cont][Col][0];
        int i1=integralLimits[idxCol][cont][Col][1]-i0;
        dvoLI=new double[i1];System.arraycopy(dVOI,i0,dvoLI,0,i1);
        svoLI=new String[i1];System.arraycopy(sVOI,i0,svoLI,0,i1);
        i0=integralLimits[idxCol][cont][Col][2];
        i1=integralLimits[idxCol][cont][Col][3]-i0;
        indexs=new int[i1][3];
        for (int i=0;i<i1;i++){System.arraycopy(indexsI[i+i0], 0, indexs[i], 0, 3);}
        i0=integralLimits[idxCol][cont][Col][4];
        i1=integralLimits[idxCol][cont][Col][5]-i0;
        indexsVar=new int[i1][2];
        for (int i=0;i<i1;i++){System.arraycopy(indexsVarI[i+i0], 0, indexsVar[i], 0, 2);}
    }
    public double resultatLI(int cont,int Col){
        actualitzarLI(cont,Col);
        return calculLI(cont,Col);
    }
    public void actualitzarLI(int cont,int Col){
        nousLimits(cont,Col);
        for(int i=0;i<indexsVar.length;i++){
            if(svoLI[indexsVar[i][0]].startsWith("-"))dvoLI[indexsVar[i][0]]=-varActLI[indexsVar[i][1]];
            else dvoLI[indexsVar[i][0]]=varActLI[indexsVar[i][1]];
        }
    }
    public double calculLI(int cont,int Col){
        if(indexs.length==0){
            if(dvoLI.length==1) {return dvoLI[0];}
        }
        for(int i=0;i<indexs.length;i++){
            dvoLI[indexs[i][1]]=Cal.caseOpFun(indexs[i][0],dvoLI[indexs[i][1]],dvoLI[indexs[i][2]]);
        }
        double d=dvoLI[indexs[indexs.length-1][1]];
        if(!isFinite(d)){
            actualitzarLI(cont,Col);
            String c="superior, ";
            if(Col==0)c="inferior, ";
            errorCalcul(svoLI,dvoLI,indexs," l"+Func.rB.getString("i_")+"mit "+c+Func.sumat+idxCol,cont);
        }
        return d;
    }
    void errorCalcul(String[] sV,double[] dv,int[][] id,String info,int cont){
        String  s;
        String[] s1=new String[dv.length];
        Func.append(0,splitPan.FIL+"error de c"+Func.rB.getString("a")+"lcul a: "+splitPan.FIL);
        Func.append(1,info);
        if(cont==-1){
            Func.append(0,splitPan.FIL+"filera:   interval  |  variable  |  l"+Func.rB.getString("i_")+"mitInferior _ lim"+Func.rB.getString("i_")+"t superior,");
            for(int i=0;i<longi[idxCol];i++){
               Func.append(0,splitPan.FIL+i+":  "+idxDim[i]+"  |  "+varAct[i]+"  |  "+dMatriuVar[idxCol][i][0]+" _ "+dMatriuVar[idxCol][i][1]);
            }
        }
        else{
            Func.append(0,splitPan.FIL+"filera:   interval  |  variable  |  l"+Func.rB.getString("i_")+"mitInferior _ l"+Func.rB.getString("i_")+"mit superior,");
            for(int i=0;i<cont;i++){
               Func.append(0,splitPan.FIL+i+":  "+idxDim[i]+"  |  "+varAct[i]+"  |  "+dMatriuVar[idxCol][i][0]+" _ "+dMatriuVar[idxCol][i][1]);
            }
        }
        Func.append(0,splitPan.FIL+"posicions i valors inicials de la funci"+Func.rB.getString("o_")+":"+splitPan.FIL);
        for(int m=0;m<dv.length;m++){
                if(isNaN(dv[m]))s="  "+sV[m];
                else s="  "+dv[m];
                s1[m]="pos: "+m+"  "+s;
        }
        for(int m=0;m<dv.length;m++){Func.append(0,s1[m]+splitPan.FIL);}
        Func.append(0,splitPan.FIL+"seq"+Func.rB.getString("u")+"encia de operacions:"+splitPan.FIL);
        s1=new String[id.length];
        for(int m=0;m<id.length;m++){
            double d=dv[id[m][1]];
            dv[id[m][1]]=Cal.caseOpFun(id[m][0],dv[id[m][1]],dv[id[m][2]]);
            String fi="",ff="";
            if(id[m][0]<0&&id[m][0]>-5){
                fi="seq: "+m+"   "+d+" (pos: "+id[m][1]+") "+simbOpFun(id[m][0])+" "+dv[id[m][2]]+" (pos: "+id[m][2]+") = ";
                ff=" (pos: "+id[m][1]+")";}
            else if(id[m][0]>=0&&id[m][0]<18){
                fi="seq: "+m+"   "+simbOpFun(id[m][0])+"("+d+") = ";
                ff="  pos: ("+id[m][1]+")";//+ "  pos: ("+id[m][2]+" > "+id[m][1]+")";
            }
            else if(id[m][0]==-5){
                fi="seq: "+m+"   "+simbOpFun(id[m][0])+"  "+d+" (pos: "+id[m][1]+") "+"  = ";
                ff=" (pos: "+id[m][1]+")";
            }
            else if(id[m][0]>12&&id[m][0]<15){
                fi="seq: "+m+"   "+simbOpFun(id[m][0])+"  "+dv[id[m][2]]+" (pos: "+id[m][2]+") "+"  = ";
                ff=" (pos: "+id[m][1]+")";
            }
            else if(id[m][0]>97){
                fi="seq: "+m+"   "+d+" (pos: "+id[m][1]+") "+simbOpFun(id[m][0])+" "+dv[id[m][2]]+" (pos: "+id[m][2]+") = ";
                ff=" (pos: "+id[m][1]+")";
            }
            Func.append(0,fi);
            if(isNaN(dv[id[m][1]])||!isFinite(dv[id[m][1]])) Func.append(1,""+dv[id[m][1]]);
            else Func.append(0,""+dv[id[m][1]]);
            Func.append(0,ff);
            Func.append(0,splitPan.FIL);
        }
        Func.append(0,splitPan.FIL);
        Cal.stopBucle=true;
    }
    //per introduir mes funcions modificar a Cal simbOpFun(int i) i  'caseOpFun(int i)'  ha Cur 'caseOpFun(int i)', a Func funcions i calculFun() a splitPan tambe els errors a Integral
   static String simbOpFun(int i){
       switch(i){
            case -5: return "-";
            case -4: return"+";
            case -3: return"/";
            case -2:return"*";
            case -1: {return "^";}
            case 1:  return "log";
            case 2:  return "ln";
            case 3:  return "sin";
            case 4:  return "asin";
            case 5:  return "cos";
            case 6:  return "acos";
            case 7:  return "tan";
            case 8:  return "atan";
            case 9:  return "abs";
            case 10:  return "rad";
            case 11:  return "graus";
            case 12:  return "rodo";
            case 13:  return "int";
            case 14:  return "fra";
            case 15:  return "sinh";
            case 16:  return "cosh";
            case 17:  return "tanh";
            case 18:  return "!";
            case 19:  return "rand";
            case 95:  return splitPan.menor;
            case 96:  return splitPan.major;
            case 97:  return "=";
            case 98:  return "<";
            case 99:  return ">"; 
        }
       return "NaN";
   }
}
