using System.IO;
using Microsoft.AspNetCore.Hosting;

namespace Interviewd
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var host = new WebHostBuilder()
                .UseKestrel()
                .UseContentRoot(Directory.GetCurrentDirectory())
                .UseIISIntegration()
                .UseStartup<Startup>()
                .UseUrls("http://*:9005")
                .Build();

            host.Run();
        }
    }
}
