using System.Diagnostics;

namespace Interviewd.Tests.Api.Rest.LikenessExtensions.Diagnostics
{
    public class DefaultDiagnosticsWriter : IDiagnosticsWriter
    {
        public void WriteMessage(string message)
        {
            Trace.WriteLine(message);
        }
    }
}