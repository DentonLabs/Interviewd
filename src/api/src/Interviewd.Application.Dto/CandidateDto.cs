using Interviewd.Common;

namespace Interviewd.Application.Dto
{
    public class CandidateDto : IIdentifiable
    {
        public string Id { get; set; }

        public string GivenName { get; set; }

        public string Surname { get; set; }
    }
}