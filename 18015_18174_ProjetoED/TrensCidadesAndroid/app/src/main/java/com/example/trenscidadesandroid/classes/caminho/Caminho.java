package com.example.trenscidadesandroid.classes.caminho;

import com.example.trenscidadesandroid.classes.cidade.Cidade;

import java.io.Serializable;

public class Caminho implements  Serializable {
    Cidade cdDeOnde, cdParaOnde;
    int tempo, distancia;

    public Cidade getCdDeOnde() {
        return cdDeOnde;
    }

    public void setCdDeOnde(Cidade cdDeOnde) {
        this.cdDeOnde = cdDeOnde;
    }

    public Cidade getCdParaOnde() {
        return cdParaOnde;
    }

    public void setCdParaOnde(Cidade cdParaOnde) {
        this.cdParaOnde = cdParaOnde;
    }

    public int getTempo() {
        return tempo;
    }

    public void setTempo(int tempo) {
        this.tempo = tempo;
    }

    public int getDistancia() {
        return distancia;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }


    public Caminho(Cidade cdDeOnde, Cidade cdParaOnde, int tempo, int distancia)
    {
        this.cdDeOnde = cdDeOnde;
        this.cdParaOnde = cdParaOnde;
        this.tempo = tempo;
        this.distancia = distancia;
    }



}
