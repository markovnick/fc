package org.clas.fcmon.ftof;

import org.clas.fcmon.tools.FCApplication;
import org.jlab.detector.base.DetectorDescriptor;
import org.root.basic.EmbeddedCanvas;
import org.root.func.F1D;
import org.root.histogram.H1D;

public class FTOFMipApp extends FCApplication {
    
//    F1D f1 = new F1D("landau",300.,3000.);

    public FTOFMipApp(String name, FTOFPixels[] ftofPix) {
        super(name,ftofPix);    
     }

    public void updateCanvas(DetectorDescriptor dd) {
        
        EmbeddedCanvas canvas = this.getCanvas(this.getName()); 
        this.getDetIndices(dd);   
        int  lr = layer;
        int ilm = ilmap;
        
        int nstr = ftofPix[ilm].nstr;
        int il=1,col0=0,col1=4,col2=2,off=0,min=0,max=nstr;
        
        H1D h;
        String alab;
        String otab[]={" Left PMT "," Right PMT "};
        String lab4[]={" ADC"," TDC","GMEAN PMT "};      
        
        off = 0;
        
        switch (ilmap) {
        case 0: canvas.divide(4,6); break;
        case 1: canvas.divide(3,7); max=21 ; if (ic>20) {min=21; max=42;} if (ic>41) {min=42; max=nstr;} break;
        case 2: canvas.divide(2,3);
        }
        
        canvas.setAxisFontSize(12);
        canvas.setAxisTitleFontSize(12);
        canvas.setTitleFontSize(14);
        canvas.setStatBoxFontSize(10);
        
        il = 1; 
        
        for(int iip=min;iip<max;iip++) {
            alab = otab[lr-1]+(iip+1)+lab4[0];
            canvas.cd(iip-min); h = ftofPix[ilm].strips.hmap2.get("H2_a_Hist").get(is,lr,0).sliceY(iip); 
            h.setXTitle(alab); h.setTitle(""); h.setFillColor(32); canvas.draw(h,"S");
            h = ftofPix[ilm].strips.hmap2.get("H2_a_Hist").get(is,0,0).sliceY(iip);
            h.setFillColor(col1); canvas.draw(h,"same");  
//            if (h.getEntries()>100) {h.fit(f1,"REQ");}
        }

        canvas.cd(ic-min); h = ftofPix[ilm].strips.hmap2.get("H2_a_Hist").get(is,0,0).sliceY(ic); 
        alab = "PMT "+(ic+1)+" GMEAN"; h.setXTitle(alab); h.setTitle(""); h.setFillColor(col2); canvas.draw(h,"S");    

    }
}
