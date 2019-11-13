﻿using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;

using Android.App;
using Android.Content;
using Android.Content.Res;
using Android.OS;
using Android.Runtime;
using Android.Views;
using Android.Widget;
using TrensCidades.Classes;

namespace TrensCidades
{
    [Activity(Label = "TrensCidades", MainLauncher = true)]
    public class AdicionarCidade : Activity
    {
        //Gustavo Henrique de Meira - 18015
        //Pedro Gomes Moreira - 18174

        Button btnAdicionar;
        EditText etNome, etCoordenadaX, etCoordenadaY;

        BucketHash<Cidade> bhCidade;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.layoutAdicionarCidade);
            btnAdicionar = FindViewById<Button>(Resource.Id.btnAdicionar);
            etNome = FindViewById<EditText>(Resource.Id.etNome);
            etCoordenadaX = FindViewById<EditText>(Resource.Id.etCoordenadaX);
            etCoordenadaY = FindViewById<EditText>(Resource.Id.etCoordenadaY);

            AssetManager assets = this.Assets;

            bhCidade = new BucketHash<Cidade>();

            using (StreamReader arq = new StreamReader(assets.Open("cidades.txt"), Encoding.UTF7))
                while (!arq.EndOfStream)
                {
                    string linha = arq.ReadLine();
                    Cidade cd = new Cidade(linha);
                    bhCidade.Inserir(cd);
                }

            btnAdicionar.Click += delegate
            {
                if (etNome.Text.Trim() != "" && etCoordenadaX.Text.Trim() != "" && etCoordenadaY.Text.Trim() != "")
                {
                    //if (bhCidade.Buscar())
                }
                else
                    Toast.MakeText(Application.Context, "Há campos vazios", ToastLength.Short);
            };
        }
    }
}