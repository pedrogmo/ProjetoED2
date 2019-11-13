using System;
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
    class AdicionarCidade : Activity
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
            btnAdicionar = FindViewById<Button>(Resource.Id.btnAdicionarCidade);
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
                    Cidade cd = new Cidade(etNome.Text.Trim());
                    if (bhCidade.Buscar(cd) == null)
                    {
                        StreamWriter sw = new StreamWriter(assets.Open("cidades.txt"));
                        cd.X = Double.Parse(etCoordenadaX.Text.Trim());
                        cd.Y = Double.Parse(etCoordenadaY.Text.Trim());
                        cd.Codigo = bhCidade.Quantidade;
                        sw.Write(cd.ParaArquivo());
                        sw.Close();
                    }
                    else
                        Toast.MakeText(Application.Context, "Essa cidade já existe", ToastLength.Short);
                }
                else
                    Toast.MakeText(Application.Context, "Há campos vazios", ToastLength.Short);
            };
        }
    }
}