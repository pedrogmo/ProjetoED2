using Android.App;
using Android.Widget;
using Android.OS;
using TrensCidades.Classes;
using System.IO;
using System.Text;
using Android.Content.Res;
using Android.Util;
using System.Collections.Generic;
using Android.Views;
using Android.Graphics;
using Android.Content;

namespace TrensCidades
{
    //Gustavo Henrique de Meira - 18015
    //Pedro Gomes Moreira - 18174

    [Activity(Label = "TrensCidades", MainLauncher = true)]
    public class MainActivity : Activity
    {
        Button btnBuscar, btnAdicionarCidade, btnAdicionarCaminho;
        Spinner spDeOnde, spParaOnde;
        LinearLayout layoutCanvas;
        CanvasView cv;

        BucketHash<Cidade> bhCidade;

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);
            SetContentView(Resource.Layout.Main);
            
            spDeOnde = FindViewById<Spinner>(Resource.Id.spDeOnde);
            spParaOnde = FindViewById<Spinner>(Resource.Id.spParaOnde);
            layoutCanvas = FindViewById<LinearLayout>(Resource.Id.layoutCanvas);
            btnBuscar = FindViewById<Button>(Resource.Id.btnBuscar);
            btnAdicionarCidade = FindViewById<Button>(Resource.Id.btnAdicionarCidadeMain);
            btnAdicionarCaminho = FindViewById<Button>(Resource.Id.btnAdicionarCaminhoMain);

            cv = new CanvasView(this);
            layoutCanvas.AddView(cv);

            AssetManager assets = this.Assets;

            bhCidade = new BucketHash<Cidade>();
            ArrayAdapter<string> cidadesSpinner = new ArrayAdapter<string>(this, Resource.Layout.item_spinner);
            
            using (StreamReader arq = new StreamReader(assets.Open("cidades.txt"), Encoding.UTF7))
                while (!arq.EndOfStream)
                {
                    string linha = arq.ReadLine();
                    Cidade cd = new Cidade(new Linha(linha));
                    cidadesSpinner.Add(cd.ToString());
                    bhCidade.Inserir(cd);
                }

            spDeOnde.Adapter = cidadesSpinner;
            spParaOnde.Adapter = cidadesSpinner;

            btnAdicionarCidade.Click += delegate
            {
                Intent i = new Intent(this, typeof(AdicionarCidade));
                StartActivity(i);
            };
            
        }
    }
}

