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
import java.util.Random;
public class Rna {
	private int numeroEntradas;
	private int numeroCapas;
	private int [] neuronasPorCapa;
	private Capa [] capas;
	private double [][][] pesos;
	private double [] pesosGeneral;
	private double []salidaObtenida;
	private int tamanoPesosGeneral;
	
	private double[][] datosPrueba;
	private double[][] salidaEsperada;
	
	private int numeroEpocas;
	private double errorDeseado;
	private double error;
	public void iniciarRna()
	{
		double error_1=0.0;
		double error_2=0.0;
		double pesosGeneralError_1[]=null;
		double pesosGeneralError_2[]=null;
		int contador=0;
		do
		{
			if(contador==0)
			{
				error_1=entrenarRna();
				//error_1=0.996607320039459;
				pesosGeneralError_1=this.pesosGeneral;	
			}
			error_2=entrenarRna();
			pesosGeneralError_2=this.pesosGeneral;
			
			this.error=Math.min(error_1,error_2);
			if(error_2 < error_1)
			{
				pesosGeneralError_1=pesosGeneralError_2;
				error_1=error_2;
			}
			System.out.println("***********************");
			System.out.println("Error 1: "+error_1+", Error 2: "+error_2+", Error: "+this.error+", Contador: "+contador);
			contador++;
		}while(this.error>this.errorDeseado && contador < this.numeroEpocas);
		this.imprimirArreglo(pesosGeneralError_1);
		this.pesosGeneral=pesosGeneralError_1;
	}
	public double entrenarRna()
	{
		this.obtenerTamanoPesosTotales();
		this.iniciarPesosGeneral();
		this.imprimirArreglo(this.pesosGeneral);
		this.iniciarPesos();
		
		this.capas=new Capa[this.numeroCapas];
		this.salidaObtenida=new double[this.datosPrueba.length];
		System.out.println("Numero capas: "+this.numeroCapas);
		System.out.println("Longitud capas: "+this.capas.length);
		double []auxiliar=null;
		int contador=0;
		for(int k=0;k<this.datosPrueba.length;k++)
		{
			System.out.println("Entradas:");
			this.imprimirArreglo(this.datosPrueba[k]);
			for(int i=0;i<this.capas.length;i++)
			{
				this.capas[i]=new Capa();
				if(i==0)
				{
					this.imprimirArreglo(this.neuronasPorCapa);
					this.capas[i].setNumeroNeuronas(1);
					this.capas[i].setEntrada(this.datosPrueba[k]);
					this.capas[i].setPeso(this.pesos[i]);
					auxiliar=this.capas[i].calcularSalida();
					this.imprimirArreglo(auxiliar);	
				}
				else
				{
					this.capas[i].setNumeroNeuronas(this.neuronasPorCapa[i]);
					this.capas[i].setEntrada(auxiliar);
					this.capas[i].setPeso(this.pesos[i]);
					auxiliar=this.capas[i].calcularSalida();
					this.imprimirArreglo(auxiliar);
				}
			}
			this.salidaObtenida[k]=auxiliar[0];
		}
		System.out.println("Salidas: \n");
		this.imprimirArreglo(this.salidaObtenida);
		System.out.println(this.error(this.salidaObtenida,this.salidaEsperada[0]));
		return this.error(this.salidaObtenida,this.salidaEsperada[0]);
	}
	public double [][] parsearArregloaMatriz(double []arreglo)
	{
		double auxiliar[][]=new double[1][arreglo.length];
		for(int i=0;i<1;i++)
		{
			for(int j=0;j<arreglo.length;j++)
			{
				auxiliar[i][j]=arreglo[j];
			}
		}
		return auxiliar;
	}
	public void imprimirArreglo(int arreglo[])
	{
		for(int j=0;j<arreglo.length;j++)
		{
			System.out.print(arreglo[j]+", ");
		}
		System.out.println();
	}
	public void imprimirArreglo(double arreglo[])
	{
		for(int j=0;j<arreglo.length;j++)
		{
			System.out.print(arreglo[j]+", ");
		}
		System.out.println();
	}
	public void obtenerTamanoPesosTotales()
	{
		this.tamanoPesosGeneral=0;
		for(int i=0;i<this.numeroCapas;i++)
		{
			if(i==0)
				this.tamanoPesosGeneral+=this.numeroEntradas*this.neuronasPorCapa[i];
			else
				this.tamanoPesosGeneral+=this.neuronasPorCapa[i-1]*this.neuronasPorCapa[i];
		}
	}
	public void iniciarPesosGeneral()
	{
		Random s=new Random();
		this.pesosGeneral=new double[this.tamanoPesosGeneral];
		for(int i=0;i<this.pesosGeneral.length;i++)
		{
			double n=s.nextDouble();
			double p=s.nextDouble();
			double num=(n*-1)+p;
			this.pesosGeneral[i]=(n*-1)+p;
		}
	}
	public void iniciarPesos()
	{		
		this.pesos=new double[this.numeroCapas][0][0];
		for(int i=0;i<this.numeroCapas;i++)
		{
			for(int j=0;j<this.neuronasPorCapa.length;j++)
			{
				if(i==0)
					this.pesos[i]=new double[this.neuronasPorCapa[i]][this.numeroEntradas];
				else
					this.pesos[i]=new double[this.neuronasPorCapa[i]][this.neuronasPorCapa[i-1]];
			}
		}
		int contador=0;
		for(int i=0;i<this.pesos.length;i++)
		{
			for(int j=0;j<this.pesos[i].length;j++)
			{
				for(int k=0;k<this.pesos[i][j].length;k++)
				{
					this.pesos[i][j][k]=this.pesosGeneral[contador];
					System.out.print(this.pesos[i][j][k]+", ");
					contador++;
				}
			}
		}
	}
	public double error(double salidaRed[],double salida[])
	{
		double error=0.0;
		for(int i=0;i<salidaRed.length;i++)
		{
			error=error+Math.pow((salidaRed[i]-salida[i]),2);
		}
		return error;
	}
	public int getNumeroEntradas() {
		return numeroEntradas;
	}
	public void setNumeroEntradas(int numeroEntradas) {
		this.numeroEntradas = numeroEntradas;
	}
	public int getNumeroCapas() {
		return numeroCapas;
	}
	public void setNumeroCapas(int numeroCapas) {
		this.numeroCapas = numeroCapas;
	}
	public int[] getNeuronasPorCapa() {
		return neuronasPorCapa;
	}
	public void setNeuronasPorCapa(int[] neuronasPorCapa) {
		this.neuronasPorCapa = neuronasPorCapa;
	}
	public double[][] getDatosPrueba() {
		return datosPrueba;
	}
	public void setDatosPrueba(double[][] datosPrueba) {
		this.datosPrueba = datosPrueba;
	}
	public double[][] getSalidaEsperada() {
		return salidaEsperada;
	}
	public void setSalidaEsperada(double[][] salidaEsperada) {
		this.salidaEsperada = salidaEsperada;
	}
	public int getNumeroEpocas() {
		return numeroEpocas;
	}
	public void setNumeroEpocas(int numeroEpocas) {
		this.numeroEpocas = numeroEpocas;
	}
	public double getErrorDeseado() {
		return errorDeseado;
	}
	public void setErrorDeseado(double errorDeseado) {
		this.errorDeseado = errorDeseado;
	}
	public double[] getPesosGeneral() {
		return pesosGeneral;
	}
	public void setPesosGeneral(double[] pesosGeneral) {
		this.pesosGeneral = pesosGeneral;
	}
	public double probarRna()
	{
		System.out.println("\n******************************PROBANDO LA RED NEURONAL XOR: ");
		this.obtenerTamanoPesosTotales();
		this.imprimirArreglo(this.pesosGeneral);
		this.iniciarPesos();
		
		this.capas=new Capa[this.numeroCapas];
		this.salidaObtenida=new double[this.datosPrueba.length];
		System.out.println("Numero capas: "+this.numeroCapas);
		System.out.println("Longitud capas: "+this.capas.length);
		double []auxiliar=null;
		for(int k=0;k<this.datosPrueba.length;k++)
		{
			System.out.println("Entradas:");
			this.imprimirArreglo(this.datosPrueba[k]);
			for(int i=0;i<this.capas.length;i++)
			{
				this.capas[i]=new Capa();
				if(i==0)
				{
					this.imprimirArreglo(this.neuronasPorCapa);
					this.capas[i].setNumeroNeuronas(1);
					this.capas[i].setEntrada(this.datosPrueba[k]);
					this.capas[i].setPeso(this.pesos[i]);
					auxiliar=this.capas[i].calcularSalida();
					this.imprimirArreglo(auxiliar);	
				}
				else
				{
					this.capas[i].setNumeroNeuronas(this.neuronasPorCapa[i]);
					this.capas[i].setEntrada(auxiliar);
					this.capas[i].setPeso(this.pesos[i]);
					auxiliar=this.capas[i].calcularSalida();
					this.imprimirArreglo(auxiliar);
				}
			}
			this.salidaObtenida[k]=auxiliar[0];
		}
		System.out.println("Prueba SALIDA:");
		this.imprimirArreglo(this.salidaObtenida);
		System.out.println("Error: "+this.error(this.salidaObtenida,this.salidaEsperada[0])+"\n************************");		
		return this.error(this.salidaObtenida,this.salidaEsperada[0]);
	}
}
