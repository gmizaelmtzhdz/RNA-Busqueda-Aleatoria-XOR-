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
public class Capa {
	private int numeroNeuronas;
	private double [][]peso;
	private double []entrada;
	
	private Neurona [] neuronas;
	private double[] salida;
	public double [] calcularSalida()
	{
		this.neuronas=new Neurona[this.numeroNeuronas];
		this.salida=new double[this.numeroNeuronas];
		for(int i=0;i<this.salida.length;i++)
		{
			this.neuronas[i]=new Neurona();
			this.salida[i]=this.neuronas[i].sigmoidal(this.entrada,this.peso[i]);
		}
		return this.salida;
	}
	public int getNumeroNeuronas() {
		return numeroNeuronas;
	}
	public void setNumeroNeuronas(int numeroNeuronas) {
		this.numeroNeuronas = numeroNeuronas;
	}
	public double[][] getPeso() {
		return peso;
	}
	public void setPeso(double[][] peso) {
		this.peso = peso;
	}
	public double[] getEntrada() {
		return entrada;
	}
	public void setEntrada(double[] entrada) {
		this.entrada = entrada;
	}
}
