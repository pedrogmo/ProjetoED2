using Android.App;
using Android.Widget;
using Android.OS;

namespace TrensCidades
{
    [Activity(Label = "TrensCidades", MainLauncher = true)]
    public class MainActivity : Activity
    {
        //Gustavo Henrique de Meira - 18015
        //Pedro Gomes Moreira - 18174

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            // Set our view from the "main" layout resource
            SetContentView(Resource.Layout.Main);
        }
    }
}

