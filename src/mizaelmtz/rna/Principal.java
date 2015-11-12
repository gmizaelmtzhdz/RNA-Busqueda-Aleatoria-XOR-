/*
*  Created on: 2015
*  Author: Mizael Martinez
*/
/*
The MIT License (MIT)

Copyright (c) 2015 Martinez Mizael

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package mizaelmtz.rna.principal;
public class Principal {

	public static void main(String[] args) {
		int numeroEntradas=2;
		int numeroCapas=2;
		int [] neuronasPorCapa={2,1};
		double[][] datosPrueba={{0,0},{0,1},{1,0},{1,1}};
		double[][] salidaEsperada={{0,1,1,0}};
		int numeroEpocas=1000000;
		double errorDeseado=0.1;
		//3 Neuronas error: 0.9970077037561607
		//double [] pesos={-0.9870651951136611, -0.9149912954887511, 0.45193062266231354, -0.31455879387743824, -0.49723148419784613, 0.36013048023728844, -0.378058839538432, -0.27430992670258825, 0.6416457714381675}
		
		//2 Neuronas error: 0.996607320039459
		//double [] pesos={-0.9628634955765593, -0.9784416900444317, -0.6669759742316119, -0.1445507364551062, -0.3642922547217422, 0.03157644024629813};
		
		// 1 Neurona error: 0.9972631627842008
		//double [] pesos={-0.9717809970927578, -0.8931757622678286, -0.334824954101933};
		
		double [] pesos={-6.124218,-6.428169,2.819766,-1.117407,-1.121139,0.936035};
		
		
		
		Rna rna=new Rna();
		rna.setNumeroEpocas(numeroEpocas);
		rna.setErrorDeseado(errorDeseado);
		rna.setNumeroEntradas(numeroEntradas);
		rna.setNumeroCapas(numeroCapas);
		rna.setNeuronasPorCapa(neuronasPorCapa);
		rna.setDatosPrueba(datosPrueba);
		rna.setSalidaEsperada(salidaEsperada);
		rna.setPesosGeneral(pesos);
		//rna.iniciarRna();
		
		rna.probarRna();
	}

}
