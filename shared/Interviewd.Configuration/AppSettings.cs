using Interviewd.Configuration;

namespace Interviewd.Configuration
{
    public class AppSettings
    {
        public ConnectionStringSettings ConnectionStrings { get; set; }

        public string ApiUri { get; set; }
    }
}